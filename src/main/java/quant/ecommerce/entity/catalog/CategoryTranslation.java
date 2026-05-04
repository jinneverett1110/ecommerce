package quant.ecommerce.entity.catalog;

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
import quant.ecommerce.entity.common.Language;

@Entity
@Table(name = "category_translations", indexes = {
        @Index(name = "idx_category_translation_cat_lang", columnList = "categoryId, languageId", unique = true)
})
@SQLDelete(sql = "UPDATE category_translations SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTranslation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "languageId", nullable = false)
    private Language language;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}