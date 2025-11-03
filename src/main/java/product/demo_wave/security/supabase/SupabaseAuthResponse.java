package product.demo_wave.security.supabase;

import lombok.Data;

/**
 * Supabase Auth APIのレスポンス
 */
@Data
public class SupabaseAuthResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String tokenType;
    private String userId;
    private String email;
}
