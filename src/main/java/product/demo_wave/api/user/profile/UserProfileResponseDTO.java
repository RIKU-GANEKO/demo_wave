package product.demo_wave.api.user.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponseDTO {
	private String firebaseUid;
	private String email;
	private String name;
	private String profileImagePath;
}
