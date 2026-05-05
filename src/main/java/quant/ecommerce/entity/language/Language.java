package quant.ecommerce.entity.language;

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

import java.util.List;

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

    @OneToMany(mappedBy = "language")
    private List<BrandTranslation> brandTranslations;

    @OneToMany(mappedBy = "language")
    private List<Usertranslation> usertranslations;

    @OneToMany(mappedBy = "language")
    private List<CategoryTranslation> categoryTranslations;

    @OneToMany(mappedBy = "language")
    private List<ProductTranslation> productTranslations;
}
