package quant.ecommerce.controller.language;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quant.ecommerce.dto.request.language.LanguageRequest;
import quant.ecommerce.dto.response.ApiResponse;
import quant.ecommerce.dto.response.language.LanguageResponse;
import quant.ecommerce.service.language.LanguageService;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

    @PostMapping
    public ResponseEntity<ApiResponse<LanguageResponse>> create(@RequestBody @Valid LanguageRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Language created successfully", languageService.create(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LanguageResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(languageService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LanguageResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(languageService.getAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LanguageResponse>> update(@PathVariable String id,
                                                                @RequestBody @Valid LanguageRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Language updated successfully", languageService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        languageService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Language deleted successfully", null));
    }
}
