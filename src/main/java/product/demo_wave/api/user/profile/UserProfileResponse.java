package product.demo_wave.api.user.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.entity.User;

public class UserProfileResponse implements APIResponse {

	@JsonProperty("user")
	private UserProfileResponseDTO user;

	public UserProfileResponse(User userEntity) {
		this.user = new UserProfileResponseDTO(
				userEntity.getSupabaseUid(),
				userEntity.getEmail(),
				userEntity.getName(),
				userEntity.getProfileImagePath()
		);
	}
}

