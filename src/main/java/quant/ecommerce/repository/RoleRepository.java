package quant.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quant.ecommerce.entity.auth.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);

    @Query("""
            SELECT r FROM Role r
            WHERE (:keyword IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<Role> search(@Param("keyword") String keyword, Pageable pageable);
}
