package quant.ecommerce.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quant.ecommerce.dto.request.auth.LoginRequest;
import quant.ecommerce.dto.request.auth.RegisterRequest;
import quant.ecommerce.dto.response.ApiResponse;
import quant.ecommerce.dto.response.auth.AuthResponse;
import quant.ecommerce.dto.request.auth.OTPRequest;
import quant.ecommerce.dto.response.auth.TokenResponse;
import quant.ecommerce.service.auth.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Register successfully",authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Login successfully",authService.login(request)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.refreshToken(request)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/otp")
    public ResponseEntity<ApiResponse<Void>> sendOTP(@RequestBody OTPRequest request) {
        authService.sendOTP(request);
        return ResponseEntity.ok(ApiResponse.success("Done!",null));
    }
}
