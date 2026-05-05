package quant.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quant.ecommerce.entity.auth.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);


    boolean existsByEmail(String email);
}
