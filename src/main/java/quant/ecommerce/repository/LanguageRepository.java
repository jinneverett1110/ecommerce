package quant.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quant.ecommerce.entity.language.Language;

public interface LanguageRepository extends JpaRepository<Language, String> {
    boolean existsById(String id);
}
