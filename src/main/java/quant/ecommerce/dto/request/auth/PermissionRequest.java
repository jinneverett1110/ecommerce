package quant.ecommerce.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import quant.ecommerce.enums.HttpMethod;

@Getter
public class PermissionRequest {
    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @NotBlank
    @Size(max = 255)
    private String path;

    @NotNull
    private HttpMethod method;
}
