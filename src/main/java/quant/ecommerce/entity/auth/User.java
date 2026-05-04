package quant.ecommerce.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import quant.ecommerce.entity.common.BaseEntity;
import quant.ecommerce.enums.UserStatus;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true),
        @Index(name = "idx_user_status", columnList = "status"),
        @Index(name = "idx_user_role", columnList = "roleId")
})
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 255)
    @Email
    @NotBlank
    private String email;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @Column
    private String avatar;

    @Column
    private String totpSecret;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;
}