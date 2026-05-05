package quant.ecommerce.dto.response.language;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LanguageResponse {
    private String id;
    private String name;
}
