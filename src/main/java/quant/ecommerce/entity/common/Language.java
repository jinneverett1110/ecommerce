package quant.ecommerce.entity.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "languages", indexes = {
        @Index(name = "idx_language_code", columnList = "code", unique = true)
})
@SQLDelete(sql = "UPDATE languages SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Language extends BaseEntity {
    @Id
    private String id;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String name;

}
