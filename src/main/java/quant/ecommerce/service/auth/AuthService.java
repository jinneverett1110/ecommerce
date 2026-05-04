package quant.ecommerce.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import quant.ecommerce.dto.request.LoginRequest;
import quant.ecommerce.dto.request.RegisterRequest;
import quant.ecommerce.dto.response.AuthResponse;
import quant.ecommerce.dto.request.OTPRequest;
import quant.ecommerce.dto.response.TokenResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    TokenResponse refreshToken(HttpServletRequest request);

    void logout(String token);

    void sendOTP(OTPRequest request);
}
