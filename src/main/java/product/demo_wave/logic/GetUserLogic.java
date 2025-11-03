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
import product.demo_wave.security.SupabaseUserDetails;

@Component
@AllArgsConstructor
public class GetUserLogic {

	private final UserRepository userRepository;

	@CustomRetry
	public User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		// Supabase認証の場合
		if (principal instanceof SupabaseUserDetails) {
			SupabaseUserDetails supabaseUser = (SupabaseUserDetails) principal;
			// Supabaseユーザーの場合は、メールアドレスでDBからユーザーを検索
			Optional<User> opUser = userRepository.findByEmail(supabaseUser.getEmail());
			if (opUser.isPresent()) {
				return opUser.get();
			} else {
				throw new UsernameNotFoundException("Supabase user not found in local database: " + supabaseUser.getEmail());
			}
		}

		throw new UsernameNotFoundException("Unknown authentication type");
	}

	public User getUserFromCache() {
		// SecurityContextからログインユーザーの詳細情報を取得
		// Supabase認証のみをサポート
		return getUser();
	}

}
