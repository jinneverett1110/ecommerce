package quant.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quant.ecommerce.entity.auth.VerificationCode;
import quant.ecommerce.enums.VerificationCodeType;
@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    VerificationCode findByEmailAndCodeAndType(String email, String code, VerificationCodeType type);
    void deleteByEmailAndType(String email, VerificationCodeType type);
}
