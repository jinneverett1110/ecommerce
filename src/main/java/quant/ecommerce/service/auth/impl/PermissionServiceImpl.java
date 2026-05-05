package quant.ecommerce.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quant.ecommerce.dto.request.auth.PermissionRequest;
import quant.ecommerce.dto.response.auth.PermissionResponse;
import quant.ecommerce.entity.auth.Permission;
import quant.ecommerce.enums.HttpMethod;
import quant.ecommerce.repository.PermissionRepository;
import quant.ecommerce.service.auth.PermissionService;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        if(permissionRepository.existsByPathAndMethod(request.getPath(), request.getMethod())){
            throw new RuntimeException("Permission already exists");
        }

        Permission permission = Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .method(request.getMethod())
                .path(request.getPath())
                .build();

        return toResponse(permissionRepository.save(permission));
    }

    @Override
    public PermissionResponse getById(Integer id) {
        return toResponse(findById(id));
    }

    @Override
    public Page<PermissionResponse> getAll(int page, int size, String keyword,
                                           HttpMethod method, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return permissionRepository.search(keyword, method, pageable)
                .map(this::toResponse);
    }

    @Override
    public PermissionResponse update(Integer id, PermissionRequest request) {
        Permission permission = findById(id);

        if (permissionRepository.existsByPathAndMethodAndIdNot(request.getPath(), request.getMethod(), id)) {
            throw new RuntimeException("Permission already exists");
        }

        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
        permission.setPath(request.getPath());
        permission.setMethod(request.getMethod());

        return toResponse(permissionRepository.save(permission));
    }

    @Override
    public void delete(Integer id) {
        permissionRepository.delete(findById(id));
    }

    private Permission findById(Integer id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
    }

    private PermissionResponse toResponse(Permission permission) {
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
}
