package quant.ecommerce.entity.social;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import quant.ecommerce.entity.auth.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages", indexes = {
        @Index(name = "idx_message_from_user", columnList = "fromUserId"),
        @Index(name = "idx_message_to_user", columnList = "toUserId"),
        @Index(name = "idx_message_conversation", columnList = "fromUserId, toUserId"),
        @Index(name = "idx_message_read_at", columnList = "readAt")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fromUserId", nullable = false)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toUserId", nullable = false)
    private User toUser;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank
    private String content;

    @Column
    private LocalDateTime readAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public boolean isRead() {
        return this.readAt != null;
    }

    public void markAsRead() {
        if (this.readAt == null) {
            this.readAt = LocalDateTime.now();
        }
    }
}