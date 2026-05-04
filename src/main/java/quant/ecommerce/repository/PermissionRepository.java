package quant.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import quant.ecommerce.entity.auth.Permission;

import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

//    @Query("""
//    SELECT COUNT(p) > 0 FROM Permission p
//    JOIN p.roles r
//    WHERE p.path = :path
//    AND p.method = :method
//    AND r.name IN :roles
//""")
//    boolean existsByPathAndMethodAndRoles(
//            @Param("path") String path,
//            @Param("method") String method,
//            @Param("roles") Set<String> roles
//    );
}

