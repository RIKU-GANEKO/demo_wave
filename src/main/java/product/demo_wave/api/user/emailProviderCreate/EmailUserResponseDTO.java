package product.demo_wave.api.user.emailProviderCreate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailUserResponseDTO {
	private String firebaseUid;
	private String email;
	private String name;
	private String profileImagePath;
}
