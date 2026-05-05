package quant.ecommerce.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import quant.ecommerce.dto.request.auth.LoginRequest;
import quant.ecommerce.dto.request.auth.RegisterRequest;
import quant.ecommerce.dto.response.auth.AuthResponse;
import quant.ecommerce.dto.request.auth.OTPRequest;
import quant.ecommerce.dto.response.auth.TokenResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    TokenResponse refreshToken(HttpServletRequest request);

    void logout(String token);

    void sendOTP(OTPRequest request);
}
