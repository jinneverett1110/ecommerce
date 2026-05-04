package quant.ecommerce.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import quant.ecommerce.entity.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "variants", indexes = {
        @Index(name = "idx_variant_product", columnList = "productId")
})
@SQLDelete(sql = "UPDATE variants SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Variant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String name; // vd: "Màu sắc", "Kích thước"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<VariantOption> options = new ArrayList<>();

    public void addOption(VariantOption option) {
        options.add(option);
        option.setVariant(this);
    }
}
