package quant.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quant.ecommerce.entity.auth.RefreshToken;
import quant.ecommerce.entity.auth.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteByUser(User user);

    RefreshToken findByToken(String token);
}
