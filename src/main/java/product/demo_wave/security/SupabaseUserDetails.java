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

    public SupabaseUserDetails(String email, String userId) {
        this.email = email;
        this.userId = userId;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // Supabase認証の場合、パスワードは不要
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