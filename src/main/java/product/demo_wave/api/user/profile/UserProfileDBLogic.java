package product.demo_wave.api.user.profile;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.annotation.CustomRetry;
import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class UserProfileDBLogic {

//	private static final Logger logger = Logger.getLogger(UserListDBLogic.class.getSimpleName());

	private final UserRepository userRepository;

	/**
	 *
	 */
	@CustomRetry
	User fetchUser(String supabaseUid) {
		return userRepository.findBySupabaseUid(supabaseUid)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + supabaseUid));
	}

}
