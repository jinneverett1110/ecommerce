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
import quant.ecommerce.entity.product.Product;

@Entity
@Table(name = "product_translations", indexes = {
        @Index(name = "idx_product_translation_prod_lang", columnList = "productId, languageId", unique = true)
})
@SQLDelete(sql = "UPDATE product_translations SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTranslation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "languageId", nullable = false)
    private Language language;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}