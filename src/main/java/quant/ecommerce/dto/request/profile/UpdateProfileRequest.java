package quant.ecommerce.dto.request.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProfileRequest {
    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 20)
    private String phoneNumber;
}
