package quant.ecommerce.dto.response.auth;

import lombok.Builder;
import lombok.Getter;
import quant.ecommerce.enums.HttpMethod;

import java.time.Instant;

@Getter
@Builder
public class PermissionResponse {
    private Integer id;
    private String name;
    private String description;
    private String path;
    private HttpMethod method;
    private Instant createdAt;
    private Instant updatedAt;
}
