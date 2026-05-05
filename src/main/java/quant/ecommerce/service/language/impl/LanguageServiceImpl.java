package quant.ecommerce.service.language.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quant.ecommerce.dto.request.language.LanguageRequest;
import quant.ecommerce.dto.response.language.LanguageResponse;
import quant.ecommerce.entity.language.Language;
import quant.ecommerce.repository.LanguageRepository;
import quant.ecommerce.service.language.LanguageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Override
    public LanguageResponse create(LanguageRequest request) {
        if (languageRepository.existsById(request.getId())) {
            throw new RuntimeException("Language with id " + request.getId() + " already exists");
        }
        Language language = Language.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
        return toResponse(languageRepository.save(language));
    }

    @Override
    public LanguageResponse getById(String id) {
        return toResponse(findById(id));
    }

    @Override
    public List<LanguageResponse> getAll() {
        return languageRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LanguageResponse update(String id, LanguageRequest request) {
        Language language = findById(id);
        language.setName(request.getName());
        return toResponse(languageRepository.save(language));
    }

    @Override
    public void delete(String id) {
        Language language = findById(id);
        languageRepository.delete(language);
    }

    private Language findById(String id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language with id " + id + " not found"));
    }

    private LanguageResponse toResponse(Language language) {
        return LanguageResponse.builder()
                .id(language.getId())
                .name(language.getName())
                .build();
    }
}
