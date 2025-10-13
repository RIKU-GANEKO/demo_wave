package product.demo_wave.api.user.emailProviderCreate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailUserResponseDTO {
	private String supabaseUid;
	private String email;
	private String name;
	private String profileImagePath;
}
