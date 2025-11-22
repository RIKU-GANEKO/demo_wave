package product.demo_wave.contact;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.security.SupabaseUserDetails;

/**
 * お問い合わせページのコントローラー
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;
    private final UserRepository userRepository;

    /**
     * 認証情報を取得してModelAndViewに追加する共通メソッド
     */
    private void addLoggedInUser(ModelAndView mv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
            !"anonymousUser".equals(authentication.getPrincipal())) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof SupabaseUserDetails) {
                SupabaseUserDetails supabaseUser = (SupabaseUserDetails) principal;
                User user = userRepository.findById(java.util.UUID.fromString(supabaseUser.getSupabaseUserId())).orElse(null);
                if (user != null) {
                    mv.addObject("loggedInUser", user);
                }
            }
        }
    }

    /**
     * お問い合わせフォームを表示
     */
    @GetMapping("/contact")
    public ModelAndView showContactForm(ModelAndView mv, HttpServletRequest request) {
        // セッションを事前に作成（CSRFトークン生成のため）
        request.getSession(true);
        addLoggedInUser(mv);
        mv.setViewName("contact");
        return mv;
    }

    /**
     * 特定商取引法に基づく表記を表示
     */
    @GetMapping("/legal")
    public ModelAndView showLegal(ModelAndView mv) {
        addLoggedInUser(mv);
        mv.setViewName("legal");
        return mv;
    }

    /**
     * お問い合わせを送信
     */
    @PostMapping("/contact")
    public ModelAndView submitContact(
            @ModelAttribute ContactForm form,
            RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();

        // バリデーション
        if (isBlank(form.getName()) || isBlank(form.getEmail()) ||
            isBlank(form.getSubject()) || isBlank(form.getMessage())) {
            addLoggedInUser(mv);
            mv.addObject("error", "すべての項目を入力してください。");
            mv.setViewName("contact");
            return mv;
        }

        // メールアドレスの簡易チェック
        if (!form.getEmail().contains("@")) {
            addLoggedInUser(mv);
            mv.addObject("error", "有効なメールアドレスを入力してください。");
            mv.setViewName("contact");
            return mv;
        }

        try {
            contactService.sendContactEmail(form);
            redirectAttributes.addFlashAttribute("success", true);
            mv.setViewName("redirect:/contact");
        } catch (Exception e) {
            log.error("Contact form submission failed", e);
            addLoggedInUser(mv);
            mv.addObject("error", "送信に失敗しました。しばらく経ってから再度お試しください。");
            mv.setViewName("contact");
        }

        return mv;
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
