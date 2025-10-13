package product.demo_wave.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
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

		Object principal = authentication.getPrincipal();

		// Supabase認証の場合はスキップ
		if (principal instanceof SupabaseUserDetails) {
			System.out.println("Supabase authentication - skipping user update");
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}

		// 既存のユーザー認証の場合
		if (principal instanceof UsersDetails) {
			UsersDetails usersDetails = (UsersDetails) principal;
			User user = userRepository.findById(usersDetails.getAccountId()).orElseThrow();
			System.out.println(" ################### user ################# : " + user);
			user.setLastLogin(LocalDateTime.now());
			userRepository.save(user);
		}

		// リダイレクト先を決定
		String targetUrl = request.getParameter("returnUrl");
		System.out.println("################### returnUrl parameter: " + targetUrl);
		if (targetUrl != null && !targetUrl.isEmpty() && targetUrl.startsWith("/")) {
			// returnUrlパラメータがある場合はそこにリダイレクト（既にコンテキストパスを含む）
			System.out.println("################### Redirecting to: " + targetUrl);
			response.sendRedirect(targetUrl);
		} else {
			// なければトップページにリダイレクト
			System.out.println("################### Redirecting to home: " + request.getContextPath() + "/");
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
