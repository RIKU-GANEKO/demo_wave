package product.demo_wave.security;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.common.annotation.CustomRetry;

@Component
@AllArgsConstructor
public class UsersDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional
	@CustomRetry
	public UsersDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOp = userRepository.findByEmail(email);
		User user = userOp.orElseThrow(() -> new UsernameNotFoundException("user not found"));

		if (user.getRoles().stream().anyMatch(role ->
				StringUtils.equals(role.getRole(), "ROLE_ADMIN") || StringUtils.equals(role.getRole(), "ROLE_USER"))) {
			return new UsersDetails(user);
		} else {
			throw new AccessDeniedException("user has no permission");
		}
	}

	public User getCurrentlyLoggedInUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UsersDetails) {
			System.out.println(" user details : " + ((UsersDetails) principal).getUser());
			return ((UsersDetails) principal).getUser();
		}
		System.out.println(" user details : null ");
		return null;
	}
}
