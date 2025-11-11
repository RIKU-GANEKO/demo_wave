package product.demo_wave.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

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
