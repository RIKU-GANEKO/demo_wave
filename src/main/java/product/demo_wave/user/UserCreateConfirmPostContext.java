package product.demo_wave.user;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.NoSuchElementException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import product.demo_wave.entity.User;
import product.demo_wave.security.SupabaseUserDetails;
import product.demo_wave.service.SupabaseService;

@RequiredArgsConstructor
class UserCreateConfirmPostContext {
    private final UserForm userForm;

    @Getter
    private final ModelAndView modelAndView;

    private final RedirectAttributes redirectAttributes;

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;
    private final HttpSession session;

    @Setter
    private UserFacadeDBLogic userFacadeDBLogic;

    @Setter
    private SupabaseService supabaseService;

    void saveUser() throws NoSuchElementException {
        // セッションからプロフィール画像データを取得
        byte[] profileImageBytes = (byte[]) session.getAttribute("profileImageBytes");
        String profileImageName = (String) session.getAttribute("profileImageName");
        String profileImageContentType = (String) session.getAttribute("profileImageContentType");

        System.out.println("セッションからプロフィール画像データを取得: " +
            (profileImageBytes != null ? "バイト配列(" + profileImageBytes.length + "bytes)" : "null") +
            ", ファイル名: " + profileImageName);

        org.springframework.web.multipart.MultipartFile profileImage = null;
        if (profileImageBytes != null && profileImageName != null) {
            // カスタムMultipartFileを作成
            profileImage = new CustomMultipartFile(
                profileImageBytes,
                "profileImage",
                profileImageName,
                profileImageContentType
            );
        }

        // プロフィール画像を含むUserFormを作成
        UserForm userFormWithImage = new UserForm(
            userForm.name(),
            userForm.email(),
            userForm.password(),
            profileImage
        );

        // ユーザーを保存し、保存されたUserエンティティを取得
        User savedUser = userFacadeDBLogic.saveUser(userFormWithImage);

        // 保存成功後、Spring SecurityのSecurityContextに認証情報を設定
        if (savedUser != null && savedUser.getId() != null) {
            try {
                // Supabaseでログインしてアクセストークンを取得
                String accessToken = supabaseService.signInUser(userForm.email(), userForm.password());

                // アクセストークンをCookieに設定
                Cookie accessTokenCookie = new Cookie("supabase-access-token", accessToken);
                accessTokenCookie.setPath("/");
                accessTokenCookie.setMaxAge(3600); // 1時間
                accessTokenCookie.setHttpOnly(false); // JavaScriptからもアクセス可能
                httpServletResponse.addCookie(accessTokenCookie);

                System.out.println("Supabaseアクセストークンをクッキーに設定しました: " + savedUser.getEmail());

                // Supabaseユーザー用の簡易UserDetailsを作成
                SupabaseUserDetails supabaseUser = new SupabaseUserDetails(
                    savedUser.getEmail(),
                    savedUser.getId().toString()
                );

                // Spring SecurityのAuthenticationを作成
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        supabaseUser,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );

                // SecurityContextに認証情報を設定
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);

                // セッションにSecurityContextを保存（これが重要！）
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

                System.out.println("ユーザー登録後に自動ログイン: " + savedUser.getEmail());
                System.out.println("SecurityContextをセッションに保存しました");
            } catch (Exception e) {
                System.err.println("Supabaseログインに失敗: " + e.getMessage());
                e.printStackTrace();
                // ログイン失敗しても処理を続行
            }
        }

        // セッションからプロフィール画像データを削除
        session.removeAttribute("profileImageBytes");
        session.removeAttribute("profileImageName");
        session.removeAttribute("profileImageContentType");
    }

    void setModelAndView() {
        // 登録完了後は直接ホーム画面に遷移
        // サーバー側で既にSupabaseログイン＆Cookie設定が完了している
        this.redirectAttributes.addFlashAttribute("signupSuccess", true);
        this.redirectAttributes.addFlashAttribute("userName", userForm.name());
        this.modelAndView.setViewName("redirect:/");
    }
}
