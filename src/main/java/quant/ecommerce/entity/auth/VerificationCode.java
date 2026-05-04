package quant.ecommerce.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import quant.ecommerce.enums.VerificationCodeType;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_codes", indexes = {
        @Index(name = "idx_verification_email_code_type", columnList = "email, code, type")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 10)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VerificationCodeType type;

    @Column(nullable = false)
    private Instant expiresAt;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    // Helper: kiểm tra mã còn hạn không
    public boolean isExpired() {
        return Instant.now().isAfter(this.expiresAt);
    }
}

