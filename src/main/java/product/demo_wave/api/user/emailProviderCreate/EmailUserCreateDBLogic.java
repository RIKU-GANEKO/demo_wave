package product.demo_wave.api.user.emailProviderCreate;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.annotation.CustomRetry;
import product.demo_wave.entity.Account;
import product.demo_wave.entity.User;
import product.demo_wave.repository.AccountRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class EmailUserCreateDBLogic {

	//	private static final Logger logger = Logger.getLogger(UserListDBLogic.class.getSimpleName());

	private final UserRepository userRepository;
	private final AccountRepository accountRepository;

	/**
	 *
	 */
	@CustomRetry
	User saveUser(String supabaseUid, String email, EmailUserRequest request) {
		User newUser = userRepository.saveAndFlush(toEntity(supabaseUid, email, request));
		return newUser;
	}

	private User toEntity(String supabaseUid, String email, EmailUserRequest request) {

		// 適当な account_id（例えば 1）を仮に入れておく。
		Account account = accountRepository.findById(1)
				.orElseThrow(() -> new NoSuchElementException("Account not found"));

		User user = new User();

		user.setSupabaseUid(supabaseUid);
		user.setAccount(account);
		user.setName(request.getName());
		user.setEmail(email);
		user.setProfileImagePath(request.getProfileImagePath());
		user.setPassword("123456");
		user.setStatus(true);
		user.setLastLogin(LocalDateTime.now());

		return user;
	}

}