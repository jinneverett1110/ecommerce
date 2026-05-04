package quant.ecommerce.entity.order;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import quant.ecommerce.entity.auth.User;
import quant.ecommerce.entity.common.BaseEntity;
import quant.ecommerce.enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_order_user", columnList = "userId"),
        @Index(name = "idx_order_status", columnList = "status")
})
@SQLDelete(sql = "UPDATE orders SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING_CONFIRMATION;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductSKUSnapshot> snapshots = new ArrayList<>();

    // Helper methods
    public boolean isCancellable() {
        return this.status == OrderStatus.PENDING_CONFIRMATION;
    }

    public boolean isReturnable() {
        return this.status == OrderStatus.DELIVERED;
    }
}
