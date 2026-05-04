package quant.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quant.ecommerce.entity.auth.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(String name);
}
