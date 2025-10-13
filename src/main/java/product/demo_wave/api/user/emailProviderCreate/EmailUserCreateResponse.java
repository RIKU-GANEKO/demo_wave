package product.demo_wave.api.user.emailProviderCreate;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.entity.User;

public class EmailUserCreateResponse implements APIResponse {

	@JsonProperty("user")
	private EmailUserResponseDTO user;

	public EmailUserCreateResponse(User userEntity) {
		this.user = new EmailUserResponseDTO(
				userEntity.getSupabaseUid(),
				userEntity.getEmail(),
				userEntity.getName(),
				userEntity.getProfileImagePath()
		);
	}
}
