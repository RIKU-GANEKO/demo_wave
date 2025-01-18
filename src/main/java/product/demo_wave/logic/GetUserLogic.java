package product.demo_wave.logic;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.security.UsersDetails;

@Component
@AllArgsConstructor
public class GetUserLogic {

	private final UserRepository userRepository;

	@CustomRetry
	public User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
		Optional<User> OpUser = userRepository.findById(usersDetails.getAccountId());
		User user = OpUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		// TODO Exception投げる際に、第2引数でExceptionを渡し、loggerで吐かせるStackTraceさせる
		return user;
	}

	public User getUserFromCache() {
		// SecurityContextからログインユーザーの詳細情報を取得
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal(); // UsersDetailsを取得
		User loggedInUser = usersDetails.getUser(); // Userオブジェクトを取得
		return loggedInUser;
	}

}
