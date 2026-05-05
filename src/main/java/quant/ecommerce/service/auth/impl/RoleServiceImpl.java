package quant.ecommerce.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quant.ecommerce.dto.request.auth.RoleRequest;
import quant.ecommerce.dto.response.auth.PermissionResponse;
import quant.ecommerce.dto.response.auth.RoleResponse;
import quant.ecommerce.entity.auth.Permission;
import quant.ecommerce.entity.auth.Role;
import quant.ecommerce.repository.PermissionRepository;
import quant.ecommerce.repository.RoleRepository;
import quant.ecommerce.service.auth.RoleService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleResponse create(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new RuntimeException("Role already exists");
        }

        List<Permission> permissions = fetchPermissions(request.getPermissionIds());

        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .permissions(permissions)
                .build();

        return toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse getById(Integer id) {
        return toResponse(findById(id));
    }

    @Override
    public Page<RoleResponse> getAll(int page, int size, String keyword, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return roleRepository.search(keyword, pageable)
                .map(this::toResponse);
    }

    @Override
    public RoleResponse update(Integer id, RoleRequest request) {
        Role role = findById(id);

        if (roleRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new RuntimeException("Role already exists");
        }

        List<Permission> permissions = fetchPermissions(request.getPermissionIds());

        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setPermissions(permissions);

        return toResponse(roleRepository.save(role));
    }

    @Override
    public void delete(Integer id) {
        roleRepository.delete(findById(id));
    }

    private Role findById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role Not Found"));
    }

    private List<Permission> fetchPermissions(List<Integer> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) return List.of();
        return permissionRepository.findAllById(permissionIds);
    }

    private PermissionResponse toPermissionResponse(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .path(permission.getPath())
                .method(permission.getMethod())
                .createdAt(permission.getCreatedAt())
                .updatedAt(permission.getUpdatedAt())
                .build();
    }

    private RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions().stream()
                        .map(this::toPermissionResponse)
                        .toList())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}
