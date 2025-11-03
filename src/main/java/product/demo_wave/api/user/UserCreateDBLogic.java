package product.demo_wave.api.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.annotation.CustomRetry;
import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class UserCreateDBLogic {

//	private static final Logger logger = Logger.getLogger(UserListDBLogic.class.getSimpleName());

	private final UserRepository userRepository;

	@CustomRetry
	List<UUID> getUserIds() {
		List<UUID> userIds = userRepository.findAllUserIds();
		return userIds;
	}

	/**
	 *
	 */
	@CustomRetry
	User saveUser(String supabaseUid, String email, UserCreateRequest request) {
		User newUser = userRepository.saveAndFlush(toEntity(supabaseUid, email, request));
		return newUser;
	}

	private User toEntity(String supabaseUid, String email, UserCreateRequest request) {

		// Account不要（Stripe + Amazonギフト券使用）

		User user = new User();

		// Supabase UUID を User の ID として設定
		user.setId(UUID.fromString(supabaseUid));
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
