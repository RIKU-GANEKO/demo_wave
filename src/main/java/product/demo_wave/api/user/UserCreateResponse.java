package product.demo_wave.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.entity.User;

public class UserCreateResponse implements APIResponse {

	@JsonProperty("user")
	private UserResponseDTO user;

	public UserCreateResponse(User userEntity) {
		this.user = new UserResponseDTO(
				userEntity.getSupabaseUid(),
				userEntity.getEmail(),
				userEntity.getName(),
				userEntity.getProfileImagePath()
		);
	}
}

