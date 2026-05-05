package quant.ecommerce.service.auth;

import org.springframework.data.domain.Page;
import quant.ecommerce.dto.request.auth.PermissionRequest;
import quant.ecommerce.dto.response.auth.PermissionResponse;
import quant.ecommerce.enums.HttpMethod;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);
    PermissionResponse getById(Integer id);
    Page<PermissionResponse> getAll(int page, int size, String keyword, HttpMethod method, String sortBy, String sortDir);
    PermissionResponse update(Integer id, PermissionRequest request);
    void delete(Integer id);
}
