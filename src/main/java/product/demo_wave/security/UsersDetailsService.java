package product.demo_wave.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class UsersDetailsService {

	private final UserRepository userRepository;

	public User getCurrentlyLoggedInUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof SupabaseUserDetails) {
			// Supabase認証の場合、userIdからUserエンティティを取得
			SupabaseUserDetails supabaseDetails = (SupabaseUserDetails) principal;
			String userId = supabaseDetails.getSupabaseUserId();
			try {
				return userRepository.findById(java.util.UUID.fromString(userId)).orElse(null);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		return null;
	}
}
