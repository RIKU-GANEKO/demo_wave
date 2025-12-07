package product.demo_wave.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        // ログインに使用したメールアドレスを取得
        String email = request.getParameter("email");
        if (email != null && !email.isEmpty()) {
            // ログイン失敗を記録
            loginAttemptService.loginFailed(email);

            // 現在の試行回数を取得
            int attempts = loginAttemptService.getAttempts(email);
            log.info("ログイン失敗: email={}, attempts={}/10", email, attempts);
        }

        // リクエストのRefererを確認して、管理者ログインページからのリクエストか判定
        String referer = request.getHeader("Referer");

        if (referer != null && referer.contains("/admin/login")) {
            // 管理者ログインページからの失敗の場合
            response.sendRedirect(request.getContextPath() + "/admin/login?error");
        } else {
            // 一般ユーザーログインページからの失敗の場合
            response.sendRedirect(request.getContextPath() + "/login?error");
        }
    }
}
