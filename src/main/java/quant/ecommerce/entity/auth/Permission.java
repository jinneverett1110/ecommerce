package quant.ecommerce.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import quant.ecommerce.entity.common.BaseEntity;

@Entity
@Table(name = "permissions", indexes = {
        @Index(name = "idx_permission_path_method", columnList = "path, method")
})
@SQLDelete(sql = "UPDATE permissions SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String path; // vd: "/api/products"

    @Column(nullable = false, length = 10)
    @NotBlank
    private String method; // vd: "GET", "POST", "PUT", "DELETE"
}
