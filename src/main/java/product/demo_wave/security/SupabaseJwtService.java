package product.demo_wave.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Supabase JWTトークンを検証するサービス
 */
@Service
public class SupabaseJwtService {

    private static final Logger logger = LoggerFactory.getLogger(SupabaseJwtService.class);

    @Value("${supabase.jwt.secret}")
    private String jwtSecret;

    @Value("${supabase.url}")
    private String supabaseUrl;

    /**
     * Supabase JWTトークンを検証し、ペイロード情報を取得する
     *
     * @param token JWTトークン文字列
     * @return SupabaseToken トークン情報
     * @throws JwtException トークンが無効な場合
     */
    public SupabaseToken verifyToken(String token) throws JwtException {
        try {
            // JWT Secretから署名鍵を生成
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            // JWTトークンをパース・検証
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // SupabaseTokenオブジェクトを構築
            SupabaseToken supabaseToken = SupabaseToken.builder()
                    .uid(claims.getSubject()) // "sub" claim
                    .email(claims.get("email", String.class))
                    .issuedAt(claims.getIssuedAt() != null ? claims.getIssuedAt().getTime() : null)
                    .expiresAt(claims.getExpiration() != null ? claims.getExpiration().getTime() : null)
                    .issuer(claims.getIssuer())
                    .role(claims.get("role", String.class))
                    .build();

            logger.debug("Supabase token verified successfully for user: {}", supabaseToken.getEmail());
            return supabaseToken;

        } catch (JwtException e) {
            logger.warn("Failed to verify Supabase token: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * トークンからユーザーIDを取得する（簡易版）
     *
     * @param token JWTトークン文字列
     * @return ユーザーID（sub claim）
     * @throws JwtException トークンが無効な場合
     */
    public String getUid(String token) throws JwtException {
        return verifyToken(token).getUid();
    }

    /**
     * トークンからメールアドレスを取得する（簡易版）
     *
     * @param token JWTトークン文字列
     * @return メールアドレス（email claim）
     * @throws JwtException トークンが無効な場合
     */
    public String getEmail(String token) throws JwtException {
        return verifyToken(token).getEmail();
    }
}
