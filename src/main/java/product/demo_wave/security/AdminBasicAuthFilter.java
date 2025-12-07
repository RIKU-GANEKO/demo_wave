package product.demo_wave.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 管理者画面専用のベーシック認証フィルター
 * Spring SecurityのSecurityFilterChainより前に実行される
 */
@Slf4j
@Component
public class AdminBasicAuthFilter extends OncePerRequestFilter {

    @Value("${admin.basic.auth.username}")
    private String adminUsername;

    @Value("${admin.basic.auth.password}")
    private String adminPassword;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        log.info("### AdminBasicAuthFilter: path={}, method={}", requestPath, request.getMethod());

        // /admin/で始まるパスのみベーシック認証を要求
        if (requestPath.contains("/admin/")) {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Basic ")) {
                // ベーシック認証ヘッダーがない場合、認証を要求
                requireBasicAuth(response);
                return;
            }

            // ベーシック認証の検証
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            String[] values = credentials.split(":", 2);

            if (values.length != 2) {
                requireBasicAuth(response);
                return;
            }

            String username = values[0];
            String password = values[1];

            // 認証情報の検証
            if (!adminUsername.equals(username) || !adminPassword.equals(password)) {
                log.warn("管理者画面へのベーシック認証失敗: username={}", username);
                requireBasicAuth(response);
                return;
            }

            log.debug("管理者画面ベーシック認証成功: {}", username);
        }

        // 認証成功 or 管理者画面以外 → 次のフィルターへ
        filterChain.doFilter(request, response);
    }

    /**
     * ベーシック認証を要求するレスポンスを返す
     */
    private void requireBasicAuth(HttpServletResponse response) throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=\"DemoWave Admin Area\"");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
