package quant.ecommerce.service.profile;

import quant.ecommerce.dto.request.profile.ChangePasswordRequest;
import quant.ecommerce.dto.request.profile.UpdateProfileRequest;
import quant.ecommerce.dto.response.profile.ProfileResponse;

public interface ProfileService {
    ProfileResponse getProfile(Integer userId);
    ProfileResponse updateProfile(Integer userId, UpdateProfileRequest request);
    void changePassword(Integer userId, ChangePasswordRequest request);
}