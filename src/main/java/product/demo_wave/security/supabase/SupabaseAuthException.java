package product.demo_wave.security.supabase;

import org.springframework.security.core.AuthenticationException;

/**
 * Supabase認証エラー
 */
public class SupabaseAuthException extends AuthenticationException {

    public SupabaseAuthException(String msg) {
        super(msg);
    }

    public SupabaseAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
