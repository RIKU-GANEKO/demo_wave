package product.demo_wave.user;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class UserCreateConfirmPostContext {
    private final UserForm userForm;

    @Getter
    private final ModelAndView modelAndView;

    private final RedirectAttributes redirectAttributes;
    
    private final HttpServletResponse httpServletResponse;
    private final HttpSession session;

    @Setter
    private UserFacadeDBLogic userFacadeDBLogic;

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
        
        userFacadeDBLogic.saveUser(userFormWithImage);
        
        // セッションからプロフィール画像データを削除
        session.removeAttribute("profileImageBytes");
        session.removeAttribute("profileImageName");
        session.removeAttribute("profileImageContentType");
    }

    void setModelAndView() {
        // 登録完了後はサインアップ成功ページに遷移してSupabaseでログイン
        String encodedEmail = URLEncoder.encode(userForm.email(), StandardCharsets.UTF_8);
        String encodedPassword = URLEncoder.encode(userForm.password(), StandardCharsets.UTF_8);
        String redirectUrl = String.format("redirect:/user/signup/success?email=%s&password=%s", 
            encodedEmail, encodedPassword);
        this.modelAndView.setViewName(redirectUrl);
    }
}
