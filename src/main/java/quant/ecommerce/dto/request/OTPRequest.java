package quant.ecommerce.dto.request;

import lombok.Getter;
import quant.ecommerce.enums.VerificationCodeType;

@Getter
public class OTPRequest {
    private String email;
    private VerificationCodeType verificationCodeType;
}
