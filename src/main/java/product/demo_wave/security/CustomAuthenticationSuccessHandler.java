package product.demo_wave.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.common.annotation.CustomRetry;

@Component
@AllArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final UserRepository userRepository;

	@Override
	@CustomRetry
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
		User user = userRepository.findById(usersDetails.getAccountId()).orElseThrow();
		System.out.println(" ################### user ################# : " + user);
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);

		response.sendRedirect("/");
	}
}
