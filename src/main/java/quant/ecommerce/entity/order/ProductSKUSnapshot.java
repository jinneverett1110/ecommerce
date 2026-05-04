package quant.ecommerce.entity.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import quant.ecommerce.entity.product.SKU;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_sku_snapshots", indexes = {
        @Index(name = "idx_snapshot_order", columnList = "orderId"),
        @Index(name = "idx_snapshot_sku", columnList = "skuId")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSKUSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Snapshot dữ liệu tại thời điểm đặt hàng — KHÔNG dùng FK tới ProductTranslation
    // vì tên/giá có thể thay đổi sau này
    @Column(nullable = false, length = 255)
    @NotBlank
    private String productName;

    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(name = "snapshot_images", joinColumns = @JoinColumn(name = "snapshotId"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Column(length = 100)
    private String skuValue; // vd: "RED-XL"

    // Giữ FK để tra cứu nếu SKU còn tồn tại, nhưng KHÔNG phụ thuộc vào nó
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skuId")
    private SKU sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
