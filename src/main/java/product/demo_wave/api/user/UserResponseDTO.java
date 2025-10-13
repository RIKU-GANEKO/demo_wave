package product.demo_wave.api.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {
	private String supabaseUid;
	private String email;
	private String name;
	private String profileImagePath;
}
