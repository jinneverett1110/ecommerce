package quant.ecommerce.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quant.ecommerce.dto.request.auth.PermissionRequest;
import quant.ecommerce.dto.response.ApiResponse;
import quant.ecommerce.dto.response.auth.PermissionResponse;
import quant.ecommerce.enums.HttpMethod;
import quant.ecommerce.service.auth.PermissionService;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> create(@RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Permission created successfully", permissionService.create(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(permissionService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PermissionResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) HttpMethod method,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                permissionService.getAll(page, size, keyword, method, sortBy, sortDir)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> update(@PathVariable Integer id,
                                                                  @RequestBody @Valid PermissionRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Permission updated successfully", permissionService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        permissionService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Permission deleted successfully", null));
    }
}
