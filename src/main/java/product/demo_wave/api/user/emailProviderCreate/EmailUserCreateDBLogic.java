package product.demo_wave.api.user.emailProviderCreate;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.annotation.CustomRetry;
import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class EmailUserCreateDBLogic {

	//	private static final Logger logger = Logger.getLogger(UserListDBLogic.class.getSimpleName());

	private final UserRepository userRepository;

	/**
	 *
	 */
	@CustomRetry
	User saveUser(String supabaseUid, String email, EmailUserRequest request) {
		User newUser = userRepository.saveAndFlush(toEntity(supabaseUid, email, request));
		return newUser;
	}

	private User toEntity(String supabaseUid, String email, EmailUserRequest request) {

		// Account不要（Stripe + Amazonギフト券使用）

		User user = new User();

		user.setId(java.util.UUID.fromString(supabaseUid));
		user.setName(request.getName());
		user.setEmail(email);
		user.setProfileImagePath(request.getProfileImagePath());
		// Supabase manages passwords in auth.users table
		// user.setPassword("123456");
		user.setStatus(true);
		user.setLastLogin(LocalDateTime.now());

		return user;
	}

}