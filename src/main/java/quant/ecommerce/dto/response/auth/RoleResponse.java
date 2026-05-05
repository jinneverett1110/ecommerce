package quant.ecommerce.dto.response.auth;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
@Getter
@Builder
public class RoleResponse {
    private Integer id;
    private String name;
    private String description;
    private List<PermissionResponse> permissions;
    private Instant createdAt;
    private Instant updatedAt;
}
