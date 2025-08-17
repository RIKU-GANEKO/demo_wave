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
			// 存在しない場合は新規作成またはSupabase情報で代替
			Optional<User> opUser = userRepository.findByEmail(supabaseUser.getEmail());
			if (opUser.isPresent()) {
				return opUser.get();
			} else {
				// ここでSupabaseユーザー用のダミーUserまたは新規作成
				throw new UsernameNotFoundException("Supabase user not found in local database: " + supabaseUser.getEmail());
			}
		}
		
		// 従来のユーザー認証の場合
		if (principal instanceof UsersDetails) {
			UsersDetails usersDetails = (UsersDetails) principal;
			Optional<User> OpUser = userRepository.findById(usersDetails.getAccountId());
			User user = OpUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			return user;
		}
		
		throw new UsernameNotFoundException("Unknown authentication type");
	}

	public User getUserFromCache() {
		// SecurityContextからログインユーザーの詳細情報を取得
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		
		// Supabase認証の場合
		if (principal instanceof SupabaseUserDetails) {
			// Supabase認証の場合はgetUser()メソッドを使用
			return getUser();
		}
		
		// 従来のユーザー認証の場合
		if (principal instanceof UsersDetails) {
			UsersDetails usersDetails = (UsersDetails) principal; // UsersDetailsを取得
			User loggedInUser = usersDetails.getUser(); // Userオブジェクトを取得
			return loggedInUser;
		}
		
		throw new UsernameNotFoundException("Unknown authentication type in getUserFromCache");
	}

}
