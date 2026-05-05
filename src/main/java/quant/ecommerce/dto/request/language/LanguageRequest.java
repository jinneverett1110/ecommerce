package quant.ecommerce.dto.request.language;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LanguageRequest {
    @NotBlank
    @Size(max = 10)
    private String id;

    @NotBlank
    @Size(max = 100)
    private String name;
}
