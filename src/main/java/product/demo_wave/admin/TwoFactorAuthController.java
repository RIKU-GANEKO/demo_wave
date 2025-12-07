package product.demo_wave.admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.security.SupabaseUserDetails;
import product.demo_wave.security.TwoFactorAuthService;

/**
 * 管理者用 二要素認証（2FA）設定コントローラー
 */
@Slf4j
@Controller
@RequestMapping("/admin/2fa")
public class TwoFactorAuthController {

    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @Autowired
    private UserRepository userRepository;

    /**
     * 2FA設定画面を表示
     */
    @GetMapping("/setup")
    public String setup(Model model, @AuthenticationPrincipal SupabaseUserDetails userDetails) {
        try {
            UUID userId = UUID.fromString(userDetails.getUserId());
            User user = userRepository.findById(userId).orElseThrow();

            // 既に2FAが有効かチェック
            boolean is2FAEnabled = user.getTwoFactorEnabled() != null && user.getTwoFactorEnabled();
            model.addAttribute("is2FAEnabled", is2FAEnabled);

            if (!is2FAEnabled) {
                // 新しいシークレットキーを生成（まだ有効化されていない場合）
                String secret;
                if (user.getTwoFactorSecret() == null || user.getTwoFactorSecret().isEmpty()) {
                    secret = twoFactorAuthService.generateSecretKey();
                    user.setTwoFactorSecret(secret);
                    userRepository.save(user);
                } else {
                    secret = user.getTwoFactorSecret();
                }

                // QRコード画像を生成
                String qrCodeImage = twoFactorAuthService.generateQRCodeImage(user.getEmail(), secret);
                model.addAttribute("qrCodeImage", qrCodeImage);
                model.addAttribute("secret", secret);
            }

            model.addAttribute("email", user.getEmail());
            return "admin/2fa-setup";

        } catch (Exception e) {
            log.error("2FA設定画面エラー", e);
            model.addAttribute("error", "エラーが発生しました");
            return "admin/2fa-setup";
        }
    }

    /**
     * 2FAを有効化
     */
    @PostMapping("/enable")
    @ResponseBody
    public ResponseEntity<?> enable(
            @RequestParam String verificationCode,
            @AuthenticationPrincipal SupabaseUserDetails userDetails) {
        try {
            UUID userId = UUID.fromString(userDetails.getUserId());
            User user = userRepository.findById(userId).orElseThrow();

            // 検証コードをチェック
            if (!twoFactorAuthService.verifyCode(user.getTwoFactorSecret(), verificationCode)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"無効な検証コードです\"}");
            }

            // 2FAを有効化
            user.setTwoFactorEnabled(true);
            userRepository.save(user);

            log.info("2FAが有効化されました: user={}", user.getEmail());
            return ResponseEntity.ok("{\"success\": true}");

        } catch (Exception e) {
            log.error("2FA有効化エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"エラーが発生しました\"}");
        }
    }

    /**
     * 2FAを無効化
     */
    @PostMapping("/disable")
    @ResponseBody
    public ResponseEntity<?> disable(@AuthenticationPrincipal SupabaseUserDetails userDetails) {
        try {
            UUID userId = UUID.fromString(userDetails.getUserId());
            User user = userRepository.findById(userId).orElseThrow();

            // 2FAを無効化
            user.setTwoFactorEnabled(false);
            user.setTwoFactorSecret(null);
            userRepository.save(user);

            log.info("2FAが無効化されました: user={}", user.getEmail());
            return ResponseEntity.ok("{\"success\": true}");

        } catch (Exception e) {
            log.error("2FA無効化エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"エラーが発生しました\"}");
        }
    }

    /**
     * 2FA検証画面（ログイン後）
     */
    @GetMapping("/verify")
    public String verifyPage(Model model, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("pending2FAUserId");
        if (userId == null) {
            return "redirect:/admin/login";
        }
        return "admin/2fa-verify";
    }

    /**
     * 2FA検証（ログイン後）
     */
    @PostMapping("/verify")
    @ResponseBody
    public ResponseEntity<?> verify(
            @RequestParam String verificationCode,
            HttpServletRequest request) {
        try {
            String userIdStr = (String) request.getSession().getAttribute("pending2FAUserId");
            if (userIdStr == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"セッションが無効です\"}");
            }

            UUID userId = UUID.fromString(userIdStr);
            User user = userRepository.findById(userId).orElseThrow();

            // 検証コードをチェック
            if (!twoFactorAuthService.verifyCode(user.getTwoFactorSecret(), verificationCode)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"無効な検証コードです\"}");
            }

            // 2FA検証成功
            request.getSession().removeAttribute("pending2FAUserId");
            request.getSession().setAttribute("2FAVerified", true);

            log.info("2FA検証成功: user={}", user.getEmail());
            return ResponseEntity.ok("{\"success\": true}");

        } catch (Exception e) {
            log.error("2FA検証エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"エラーが発生しました\"}");
        }
    }
}
