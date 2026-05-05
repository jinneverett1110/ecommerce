package quant.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quant.ecommerce.entity.auth.Permission;
import quant.ecommerce.enums.HttpMethod;

import java.util.Set;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    boolean existsByPathAndMethod(String path, HttpMethod method);
    boolean existsByPathAndMethodAndIdNot(String path, HttpMethod method, Integer id);

    @Query("""
            SELECT p FROM Permission p
            WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(p.path) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:method IS NULL OR p.method = :method)
            """)
    Page<Permission> search(
            @Param("keyword") String keyword,
            @Param("method") HttpMethod method,
            Pageable pageable
    );
}

