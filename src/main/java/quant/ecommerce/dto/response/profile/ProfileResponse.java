package quant.ecommerce.dto.response.profile;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ProfileResponse {
    private Integer id;
    private String email;
    private String name;
    private String phoneNumber;
    private String avatar;
    private String role;
    private Instant createdAt;
    private Instant updatedAt;
}
