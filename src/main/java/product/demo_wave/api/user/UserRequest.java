package product.demo_wave.api.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class UserRequest {
	private final String name;
	private final String profileImagePath;

	@JsonCreator
	public UserRequest(@JsonProperty("name") String name, @JsonProperty("imageUrl") String profileImagePath) {
		this.name = name;
		this.profileImagePath = profileImagePath;
	}
}

