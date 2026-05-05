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
import quant.ecommerce.entity.catalog.Brand;
import quant.ecommerce.entity.common.BaseEntity;

@Entity
@Table(name = "brand_translations", indexes = {
        @Index(name = "idx_brand_translation_brand_lang", columnList = "brandId, languageId", unique = true)
})
@SQLDelete(sql = "UPDATE brand_translations SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BrandTranslation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "languageId", nullable = false)
    private Language language;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}