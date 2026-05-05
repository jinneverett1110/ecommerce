package quant.ecommerce.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quant.ecommerce.dto.request.auth.RoleRequest;
import quant.ecommerce.dto.response.ApiResponse;
import quant.ecommerce.dto.response.auth.RoleResponse;
import quant.ecommerce.service.auth.RoleService;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> create(@RequestBody @Valid RoleRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Role created successfully", roleService.create(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(roleService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<RoleResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                roleService.getAll(page, size, keyword, sortBy, sortDir)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> update(@PathVariable Integer id,
                                                            @RequestBody @Valid RoleRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Role updated successfully", roleService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Role deleted successfully", null));
    }
}