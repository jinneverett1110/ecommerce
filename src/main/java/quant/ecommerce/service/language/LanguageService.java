package quant.ecommerce.service.language;

import quant.ecommerce.dto.request.language.LanguageRequest;
import quant.ecommerce.dto.response.language.LanguageResponse;

import java.util.List;

public interface LanguageService {
    LanguageResponse create(LanguageRequest request);
    LanguageResponse getById(String id);
    List<LanguageResponse> getAll();
    LanguageResponse update(String id, LanguageRequest request);
    void delete(String id);
}
