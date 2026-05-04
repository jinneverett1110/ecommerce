package quant.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quant.ecommerce.entity.auth.VerificationCode;
import quant.ecommerce.enums.VerificationCodeType;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    VerificationCode findByEmailAndCodeAndType(String email, String code, VerificationCodeType type);
    void deleteByEmailAndType(String email, VerificationCodeType type);
}
