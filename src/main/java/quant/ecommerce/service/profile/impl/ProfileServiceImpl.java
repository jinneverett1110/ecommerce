package quant.ecommerce.service.profile.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quant.ecommerce.dto.request.profile.ChangePasswordRequest;
import quant.ecommerce.dto.request.profile.UpdateProfileRequest;
import quant.ecommerce.dto.response.profile.ProfileResponse;
import quant.ecommerce.entity.auth.User;
import quant.ecommerce.repository.UserRepository;
import quant.ecommerce.service.profile.ProfileService;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProfileResponse getProfile(Integer userId) {
        return toResponse(findById(userId));
    }

    @Override
    public ProfileResponse updateProfile(Integer userId, UpdateProfileRequest request) {
        User user = findById(userId);
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        return toResponse(userRepository.save(user));
    }

    @Override
    public void changePassword(Integer userId, ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords don't match");
        }

        User user = findById(userId);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Passwords don't match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private User findById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private ProfileResponse toResponse(User user) {
        return ProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .role(user.getRole().getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
