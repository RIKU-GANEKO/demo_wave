package product.demo_wave.security.supabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.security.SupabaseUserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Supabase認証プロバイダー
 * Spring SecurityのAuthenticationProviderを実装し、Supabase Auth APIで認証を行う
 */
@Component
public class SupabaseAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(SupabaseAuthenticationProvider.class);

    @Autowired
    private SupabaseAuthService supabaseAuthService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        logger.debug("Attempting Supabase authentication for: {}", email);

        try {
            // Supabase Auth APIでログイン
            SupabaseAuthResponse authResponse = supabaseAuthService.signInWithPassword(email, password);

            // ユーザーIDでDBからユーザー情報を取得
            UUID userId = UUID.fromString(authResponse.getUserId());
            Optional<User> userOpt = userRepository.findById(userId);

            if (userOpt.isEmpty()) {
                logger.error("User not found in database for Supabase user ID: {}", authResponse.getUserId());
                throw new UsernameNotFoundException("User not found in database");
            }

            User user = userOpt.get();

            // 権限を取得
            List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());

            // SupabaseUserDetailsを作成
            SupabaseUserDetails userDetails = new SupabaseUserDetails(
                user.getId().toString(),
                user.getEmail(),
                authorities,
                authResponse.getAccessToken(),
                authResponse.getRefreshToken()
            );

            logger.info("Supabase authentication successful for: {}", email);

            // 認証成功: UsernamePasswordAuthenticationTokenを返す
            return new UsernamePasswordAuthenticationToken(
                userDetails,
                authResponse.getAccessToken(),
                authorities
            );

        } catch (SupabaseAuthException e) {
            logger.error("Supabase authentication failed for {}: {}", email, e.getMessage());
            throw new BadCredentialsException("Invalid email or password", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
