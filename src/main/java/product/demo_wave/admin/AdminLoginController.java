package product.demo_wave.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 管理者専用ログインコントローラー
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    /**
     * 管理者ログインページ
     */
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "メールアドレスまたはパスワードが正しくありません。");
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", "ログアウトしました。");
        }

        return "admin/login";
    }
}
