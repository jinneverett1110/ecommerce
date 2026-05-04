package quant.ecommerce.entity.social;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import quant.ecommerce.entity.auth.User;
import quant.ecommerce.entity.product.Product;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "idx_review_product", columnList = "productId"),
        @Index(name = "idx_review_user", columnList = "userId"),
        @Index(name = "idx_review_product_user", columnList = "productId, userId", unique = true)
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @Min(value = 1, message = "Điểm đánh giá tối thiểu là 1")
    @Max(value = 5, message = "Điểm đánh giá tối đa là 5")
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean isPositive() {
        return this.rating >= 4;
    }

    public boolean isNegative() {
        return this.rating <= 2;
    }
}
