package quant.ecommerce.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import quant.ecommerce.entity.common.BaseEntity;

@Entity
@Table(name = "variant_options", indexes = {
        @Index(name = "idx_variant_option_variant", columnList = "variantId")
})
@SQLDelete(sql = "UPDATE variant_options SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VariantOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String value; // vd: "Đỏ", "XL"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variantId", nullable = false)
    private Variant variant;
}
