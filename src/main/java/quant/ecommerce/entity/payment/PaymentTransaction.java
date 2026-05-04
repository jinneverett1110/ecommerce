package quant.ecommerce.entity.payment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions", indexes = {
        @Index(name = "idx_payment_gateway", columnList = "gateway"),
        @Index(name = "idx_payment_reference", columnList = "referenceNumber"),
        @Index(name = "idx_payment_code", columnList = "code"),
        @Index(name = "idx_payment_account", columnList = "accountNumber")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Tên cổng thanh toán: "VNPAY", "MOMO", "ZALOPAY", v.v.
    @Column(nullable = false, length = 50)
    @NotBlank
    private String gateway;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false, length = 50)
    private String accountNumber;

    @Column(length = 50)
    private String subAccount; // nullable

    // Số tiền vào (đơn vị: VNĐ hoặc đơn vị nhỏ nhất)
    @Column(nullable = false)
    @Builder.Default
    private Long amountIn = 0L;

    // Số tiền ra
    @Column(nullable = false)
    @Builder.Default
    private Long amountOut = 0L;

    // Số dư lũy kế
    @Column(nullable = false)
    @Builder.Default
    private Long accumulated = 0L;

    // Mã giao dịch nội bộ
    @Column(length = 100)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String transactionContent;

    // Mã tham chiếu từ cổng thanh toán
    @Column(length = 100)
    private String referenceNumber;

    // Raw body từ webhook (lưu để debug/audit)
    @Column(columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public boolean isCredit() {
        return this.amountIn > 0;
    }

    public boolean isDebit() {
        return this.amountOut > 0;
    }
}
