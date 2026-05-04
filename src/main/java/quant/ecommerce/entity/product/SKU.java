package quant.ecommerce.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import quant.ecommerce.entity.common.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skus", indexes = {
        @Index(name = "idx_sku_product", columnList = "productId")
})
@SQLDelete(sql = "UPDATE skus SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SKU extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String value; // vd: "RED-XL"

    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Min(0)
    @Builder.Default
    private Integer stock = 0;

    // Lưu mảng URL ảnh dưới dạng JSON string
    // Nếu dùng PostgreSQL có thể dùng @JdbcTypeCode(SqlTypes.JSON)
    @ElementCollection
    @CollectionTable(name = "sku_images", joinColumns = @JoinColumn(name = "skuId"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "skus_variant_options",
            joinColumns = @JoinColumn(name = "skuId"),
            inverseJoinColumns = @JoinColumn(name = "variantOptionId")
    )
    @Builder.Default
    private List<VariantOption> variantOptions = new ArrayList<>();

    public boolean isInStock() {
        return this.stock != null && this.stock > 0;
    }

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalStateException("Không đủ hàng trong kho");
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }
}