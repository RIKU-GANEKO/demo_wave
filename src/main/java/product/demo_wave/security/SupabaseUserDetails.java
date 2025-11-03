package product.demo_wave.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

/**
 * Supabase認証ユーザー用のUserDetails実装
 */
@Getter
public class SupabaseUserDetails implements UserDetails {

    private final String email;
    private final String userId;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String accessToken;
    private final String refreshToken;

    // 旧コンストラクタ（後方互換性のため）
    public SupabaseUserDetails(String email, String userId) {
        this(userId, email, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), null, null);
    }

    // 新コンストラクタ（トークンを含む）
    public SupabaseUserDetails(
            String userId,
            String email,
            Collection<? extends GrantedAuthority> authorities,
            String accessToken,
            String refreshToken) {
        this.userId = userId;
        this.email = email;
        this.authorities = authorities;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        // Supabase manages passwords in auth.users table
        // Return empty string since password validation is not performed during Supabase authentication
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    // Supabase固有のメソッド
    public String getSupabaseUserId() {
        return userId;
    }
}