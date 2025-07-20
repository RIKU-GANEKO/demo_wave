package product.demo_wave.api.user.emailProviderCreate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class EmailUserRequest {
	private final String name;
	private final String profileImagePath;

	@JsonCreator
	public EmailUserRequest(@JsonProperty("name") String name, @JsonProperty("imageUrl") String profileImagePath) {
		this.name = name;
		this.profileImagePath = profileImagePath;
	}
}
