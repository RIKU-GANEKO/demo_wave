package product.demo_wave.security;

import lombok.Builder;
import lombok.Getter;

/**
 * Supabase JWTトークンのペイロード情報を保持するクラス
 */
@Getter
@Builder
public class SupabaseToken {
    /**
     * ユーザーID (sub claim)
     */
    private String uid;

    /**
     * メールアドレス (email claim)
     */
    private String email;

    /**
     * トークン発行時刻 (iat claim)
     */
    private Long issuedAt;

    /**
     * トークン有効期限 (exp claim)
     */
    private Long expiresAt;

    /**
     * トークンの発行者 (iss claim)
     */
    private String issuer;

    /**
     * ユーザーロール (role claim)
     */
    private String role;
}
