package quant.ecommerce.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class RoleRequest {
    @NotBlank
    @Size(max = 100)
    private String name;

    private String description;

    private List<Integer> permissionIds;
}
