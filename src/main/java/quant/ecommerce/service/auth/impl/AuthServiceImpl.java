package quant.ecommerce.service.auth.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quant.ecommerce.dto.request.auth.LoginRequest;
import quant.ecommerce.dto.request.auth.OTPRequest;
import quant.ecommerce.dto.request.auth.RegisterRequest;
import quant.ecommerce.dto.response.auth.AuthResponse;
import quant.ecommerce.dto.response.auth.TokenResponse;
import quant.ecommerce.entity.auth.RefreshToken;
import quant.ecommerce.entity.auth.Role;
import quant.ecommerce.entity.auth.User;
import quant.ecommerce.entity.auth.VerificationCode;
import quant.ecommerce.enums.VerificationCodeType;
import quant.ecommerce.repository.RefreshTokenRepository;
import quant.ecommerce.repository.RoleRepository;
import quant.ecommerce.repository.UserRepository;
import quant.ecommerce.repository.VerificationCodeRepository;
import quant.ecommerce.security.CustomUserDetails;
import quant.ecommerce.service.auth.AuthService;
import quant.ecommerce.service.email.EmailService;

import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;

    private Role role;
    @Value("${otp.expires_in}")
    private Long OTPExpiration;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already taken");
        }
        String email = request.getEmail();
        String otp = request.getOtp();
        VerificationCodeType type = VerificationCodeType.REGISTER;
        VerificationCode verificationCode = verificationCodeRepository.findByEmailAndCodeAndType(email, otp, type);

        if(verificationCode == null){
            throw new RuntimeException("Invalid verification code");
        }else if(verificationCode.isExpired()){
            throw new RuntimeException("Verification code has expired");
        }
        verificationCodeRepository.delete(verificationCode);


        if (role==null) {
            role = roleRepository.findByName("CLIENT");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .password(hashedPassword)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(role)
                .build();

        userRepository.save(user);
        CustomUserDetails userDetails = CustomUserDetails.from(user);
        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = jwtService.generateRefreshToken(user);


        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail());

        refreshTokenRepository.deleteByUser(user);

        CustomUserDetails userDetails = CustomUserDetails.from(user);
        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = jwtService.generateRefreshToken(user);


        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public TokenResponse refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Refresh token is missing");
        }

        String refreshTokenStr = authHeader.substring(7);

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr);

        if (refreshToken == null) {
            throw new RuntimeException("Refresh token not found");
        }
        if (refreshToken.isExpired()) {
            throw new RuntimeException("Refresh token has expired");
        }

        CustomUserDetails userDetails = CustomUserDetails.from(refreshToken.getUser());
        String accessToken = jwtService.generateAccessToken(userDetails);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    public void logout(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken == null) {
            throw new RuntimeException("Refresh token not found");
        }

        refreshTokenRepository.delete(refreshToken);
    }

    @Override
    @Transactional
    public void sendOTP(OTPRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if(user!=null){
            throw new RuntimeException("Email already taken");
        }

        verificationCodeRepository.deleteByEmailAndType(
                request.getEmail(),
                request.getVerificationCodeType()
        );

        String code = String.format("%06d", new Random().nextInt(900000) + 100000);

        VerificationCode verificationCode = VerificationCode.builder()
                .email(request.getEmail())
                .code(code)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(OTPExpiration))
                .type(request.getVerificationCodeType())
                .build();
        emailService.sentOTP(request.getEmail(), code);

        verificationCodeRepository.save(verificationCode);
    }


}
