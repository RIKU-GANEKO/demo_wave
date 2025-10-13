package product.demo_wave.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * カスタムログアウトハンドラー
 * Supabase関連のCookieを確実に削除する
 */
@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("===== Custom Logout Handler =====");

        // すべてのCookieを確認して削除
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Found cookie: " + cookie.getName());

                // Supabase関連のCookieを削除
                if (cookie.getName().equals("supabase-access-token") ||
                    cookie.getName().equals("supabase-refresh-token") ||
                    cookie.getName().startsWith("sb-") ||
                    cookie.getName().equals("JSESSIONID")) {

                    System.out.println("Deleting cookie: " + cookie.getName());

                    // Cookieを削除（Max-Age=0に設定）
                    Cookie deleteCookie = new Cookie(cookie.getName(), null);
                    deleteCookie.setPath("/");
                    deleteCookie.setMaxAge(0);
                    deleteCookie.setHttpOnly(false);
                    response.addCookie(deleteCookie);

                    // コンテキストパスありの場合も削除
                    Cookie deleteContextCookie = new Cookie(cookie.getName(), null);
                    deleteContextCookie.setPath("/demo_wave");
                    deleteContextCookie.setMaxAge(0);
                    deleteContextCookie.setHttpOnly(false);
                    response.addCookie(deleteContextCookie);
                }
            }
        }

        System.out.println("===== Logout Handler Complete =====");
    }
}
