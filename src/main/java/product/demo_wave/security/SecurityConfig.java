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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    UserDetailsService userDetailsService;
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
    @Autowired
    private SupabaseAuthenticationFilter supabaseAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try {
            http.authorizeHttpRequests(authorize -> {
                authorize
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
                        .requestMatchers("/lp.html").permitAll() // ← これが必要！
                        .anyRequest().authenticated();
                    });
            http.formLogin(form -> {
                form.usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/demo")
                        .permitAll();
            });
            http.logout(form -> {
                form.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll();
            });
            // CSRFの設定を追加
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/payment/**"); // WebhookエンドポイントのみCSRF無効化
            });
//            postメソッドなら必要
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/{demoId}/comment/create/**"); // "/{demoId}/comment/create/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/demo/create/**"); // "/api/demo/create/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/user/create/**"); // "/api/user/create/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/demo/participate/**"); // "/api/demo/participate/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/demo/participation-status**"); // "/api/demo/participate/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/user/get/**"); // "/api/demo/participate/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/demoList/**"); // "/api/demoList/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/payment/**"); // "/api/payment/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/demo/search/**"); // "/api/payment/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/demo/today/**"); // "/api/payment/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/location/**"); // "/api/payment/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/demo/favorite/**"); // "/api/payment/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/demo/favorite-status**"); // "/api/payment/**" のみ CSRF 無効化
            });
            http.csrf(csrf -> {
                csrf.ignoringRequestMatchers("/api/ranking/**"); // "/api/payment/**" のみ CSRF 無効化
            });
//            http.csrf(csrf -> csrf.disable());

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
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(Collections.singletonList(provider));
    }

//    /**
//     * アクセス制限を解除する path を指定する
//     * @param web
//     * @throws Exception
//     */
////    @Override
//    public void configure(WebSecurity web) {
//        web
//                .ignoring().antMatchers(
//                        // 新 api 系
//                        "/demoList**"
//                );
//    }

    /*
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("yama3")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER");
//        System.out.println(passwordEncoder().encode("123456"));
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/signup").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll().and()
                .logout().permitAll();
    }
     */
}
