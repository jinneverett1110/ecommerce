package quant.ecommerce.service.auth;

import org.springframework.data.domain.Page;
import quant.ecommerce.dto.request.auth.RoleRequest;
import quant.ecommerce.dto.response.auth.RoleResponse;

public interface RoleService {
    RoleResponse create(RoleRequest request);
    RoleResponse getById(Integer id);
    Page<RoleResponse> getAll(int page, int size, String keyword, String sortBy, String sortDir);
    RoleResponse update(Integer id, RoleRequest request);
    void delete(Integer id);
}
