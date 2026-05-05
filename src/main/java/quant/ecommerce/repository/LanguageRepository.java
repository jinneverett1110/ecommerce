package quant.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quant.ecommerce.entity.language.Language;
@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
    boolean existsById(String id);
}
