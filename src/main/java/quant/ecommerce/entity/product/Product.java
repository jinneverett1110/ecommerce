package quant.ecommerce.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import quant.ecommerce.entity.catalog.Brand;
import quant.ecommerce.entity.catalog.Category;
import quant.ecommerce.entity.common.BaseEntity;
import quant.ecommerce.entity.language.ProductTranslation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_brand", columnList = "brandId")
})
@SQLDelete(sql = "UPDATE products SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Dùng BigDecimal thay vì float để tránh sai số tài chính
    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal basePrice;

    // Giá ảo để hiển thị "giá gốc bị gạch" khi sale
    @Column(precision = 15, scale = 2)
    private BigDecimal virtualPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductTranslation> translations = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SKU> skus = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Variant> variants = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId")
    )
    @Builder.Default
    private List<Category> categories = new ArrayList<>();

    // Helper methods
    public boolean isOnSale() {
        return this.virtualPrice != null && this.virtualPrice.compareTo(this.basePrice) > 0;
    }

    public void addTranslation(ProductTranslation translation) {
        translations.add(translation);
        translation.setProduct(this);
    }

    public void addSku(SKU sku) {
        skus.add(sku);
        sku.setProduct(this);
    }

    public void addVariant(Variant variant) {
        variants.add(variant);
        variant.setProduct(this);
    }
}