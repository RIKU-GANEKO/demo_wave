package product.demo_wave.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
import product.demo_wave.security.supabase.SupabaseAuthenticationProvider;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private SupabaseAuthenticationFilter supabaseAuthenticationFilter;

    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    @Autowired
    private SupabaseAuthenticationProvider supabaseAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try {
            // セッション管理を有効化
            http.securityContext(securityContext ->
                securityContext.requireExplicitSave(false)
            );

            http.authorizeHttpRequests(authorize -> {
                authorize
                        // 管理者ルート
                        .requestMatchers("/admin/login").permitAll() // 管理者ログインページは認証不要
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 管理者画面はADMINロール必須
                        // 一般ユーザー向けルート
//                        .requestMatchers("/demo").permitAll()
                        .requestMatchers("/demoList/**").permitAll()
                        .requestMatchers("/{demoId}/commentList/**").permitAll()
                        .requestMatchers("/{demoId}/comment/create/**").permitAll()
                        .requestMatchers("/api/demo/create/**").permitAll()
                        .requestMatchers("/api/user/create/**").permitAll()
                        .requestMatchers("/api/user/current").authenticated()
                        .requestMatchers("/api/demo/participate/**").permitAll()
                        .requestMatchers("/api/demo/participation-status**").permitAll()
                        .requestMatchers("/api/user/get/**").permitAll()
                        .requestMatchers("/api/payment/**").permitAll()
                        .requestMatchers("/api/demo/search/**").permitAll()
                        .requestMatchers("/api/demo/today/**").permitAll()
                        .requestMatchers("/api/location/**").permitAll()
                        .requestMatchers("/api/demo/favorite/**").permitAll()
                        .requestMatchers("/api/demo/favorite-status**").permitAll()
                        .requestMatchers("/api/ranking/**").permitAll()
                        .requestMatchers("/api/config/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/user/signup").permitAll()
                        .requestMatchers("/user/create/confirm").permitAll()
                        .requestMatchers("/user/create/complete").permitAll()
                        .requestMatchers("/user/signup/success").permitAll()
                        .requestMatchers("/payment/**").permitAll()
                        .requestMatchers("/js/**", "/css/**", "/images/**", "favicon.ico").permitAll()
                        .requestMatchers("/resources/**", "/static/**", "/public/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/").permitAll() // ルートパス（ホーム）を認証不要に設定
                        .requestMatchers("/about").permitAll() // Demo Waveとはページを認証不要に設定
                        .requestMatchers("/organizer-guide").permitAll() // 主催者向けガイドページを認証不要に設定
                        .requestMatchers("/privacy").permitAll() // プライバシーポリシーを認証不要に設定
                        .requestMatchers("/terms").permitAll() // 利用規約を認証不要に設定
                        .requestMatchers("/search").permitAll() // 検索ページを認証不要に設定
                        .requestMatchers("/search**").permitAll() // 検索ページ（クエリパラメータ付き）を認証不要に設定
                        .requestMatchers("/demo/show").permitAll() // デモ詳細ページを認証不要に設定
                        .requestMatchers("/demo/show**").permitAll() // デモ詳細ページ（クエリパラメータ付き）を認証不要に設定
                        .anyRequest().authenticated();
                    });
            http.formLogin(form -> {
                form.usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll();
            });
            http.logout(form -> {
                form.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .addLogoutHandler(customLogoutHandler)  // カスタムログアウトハンドラーを追加
                        .deleteCookies("JSESSIONID", "supabase-access-token", "supabase-refresh-token")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll();
            });
            // CSRF設定：外部Webhook（Stripe）のみ無効化
            // SPAフロントエンドからのAPI呼び出しはSupabase JWTで認証されるためCSRF保護不要
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers(
                    "/payment/webhook",           // Stripe Webhook専用
                    "/api/**",                    // SPA用APIエンドポイント（JWT認証済み）
                    "/demoList/**",               // 公開API（読み取りのみ）
                    "/{demoId}/commentList/**"    // 公開API（読み取りのみ）
                );
            });

            // SupabaseAuthenticationFilterを追加
            http.addFilterBefore(supabaseAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        } catch (Exception e) {
            log.error("filterchainでエラーが発生しました:{}", e.getMessage(), e);
            throw e;
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        // Supabase認証プロバイダーを使用
        // DaoAuthenticationProviderは使用しない（パスワードはSupabaseで管理）
        return new ProviderManager(Collections.singletonList(supabaseAuthenticationProvider));
    }
}
