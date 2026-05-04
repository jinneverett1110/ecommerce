package quant.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quant.ecommerce.entity.auth.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);


    boolean existsByEmail(String email);
}
