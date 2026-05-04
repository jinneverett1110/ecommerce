package quant.ecommerce.entity.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import quant.ecommerce.entity.product.SKU;
import quant.ecommerce.entity.auth.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items", indexes = {
        @Index(name = "idx_cart_item_user", columnList = "userId"),
        @Index(name = "idx_cart_item_sku", columnList = "skuId"),
        @Index(name = "idx_cart_item_user_sku", columnList = "userId, skuId", unique = true)
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "sku"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Min(value = 1, message = "Số lượng phải ít nhất là 1")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skuId", nullable = false)
    private SKU sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
