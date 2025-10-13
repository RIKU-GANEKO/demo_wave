package product.demo_wave.security;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * SupabaseのJWTトークンを検証し、Spring SecurityのAuthenticationに変換するフィルター
 */
@Component
public class SupabaseAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SupabaseAuthenticationFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SupabaseJwtService supabaseJwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {

        // Supabaseセッション情報をCookieから取得
        String supabaseAccessToken = getSupabaseTokenFromCookies(request);

        if (supabaseAccessToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // SupabaseJwtServiceを使用してトークンを検証
                SupabaseToken token = supabaseJwtService.verifyToken(supabaseAccessToken);

                logger.info("Supabase authentication successful for user: {}", token.getEmail());

                // Supabaseユーザー用の簡易UserDetailsを作成
                SupabaseUserDetails supabaseUser = new SupabaseUserDetails(token.getEmail(), token.getUid());

                // Spring SecurityのAuthenticationを作成
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        supabaseUser,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (JwtException e) {
                logger.warn("Failed to validate Supabase token: {}", e.getMessage());
            }
        }
        
        filterChain.doFilter(request, response);
    }

    /**
     * リクエストからSupabaseのアクセストークンを取得
     * - Cookieから `supabase-access-token` を探す
     * - Authorizationヘッダーからも探す
     */
    private String getSupabaseTokenFromCookies(HttpServletRequest request) {
        // Cookieから取得
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                logger.debug("Checking cookie: {} = {}", cookie.getName(), cookie.getValue().substring(0, Math.min(20, cookie.getValue().length())) + "...");
                
                if ("supabase-access-token".equals(cookie.getName())) {
                    logger.info("Found Supabase access token cookie");
                    return cookie.getValue();
                }
                
                // Supabase自動生成cookieもチェック
                if (cookie.getName().startsWith("sb-") && cookie.getName().contains("auth-token")) {
                    logger.debug("Found Supabase cookie: {}", cookie.getName());
                    try {
                        // Supabase cookieは通常JSON形式
                        JsonNode cookieJson = objectMapper.readTree(cookie.getValue());
                        if (cookieJson.has("access_token")) {
                            return cookieJson.get("access_token").asText();
                        }
                    } catch (Exception e) {
                        logger.debug("Failed to parse Supabase cookie: {}", e.getMessage());
                    }
                }
            }
        }
        
        // Authorizationヘッダーから取得
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        
        // パラメータとして渡される場合もある
        String tokenParam = request.getParameter("access_token");
        if (tokenParam != null) {
            return tokenParam;
        }
        
        return null;
    }

    /**
     * 静的リソースや認証不要なパスはスキップ
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/demo_wave/js/") ||
               path.startsWith("/demo_wave/css/") ||
               path.startsWith("/demo_wave/images/") ||
               path.startsWith("/demo_wave/api/config/") ||
               path.equals("/demo_wave/login") ||
               path.equals("/demo_wave/lp.html") ||
               path.equals("/demo_wave/user/signup") ||  // 新規登録フォームのみ
               path.equals("/demo_wave/user/create/confirm");  // 確認画面のみ
    }
}