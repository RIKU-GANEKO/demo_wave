package product.demo_wave.api;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.security.SupabaseUserDetails;

/**
 * ユーザー関連のAPIエンドポイント
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);
    
    @Autowired
    private UserRepository userRepository;

    /**
     * 現在ログイン中のユーザー情報を取得
     */
    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                logger.warn("User not authenticated");
                return ResponseEntity.status(401).body(Map.of("error", "認証が必要です"));
            }

            String email = null;
            if (authentication.getPrincipal() instanceof SupabaseUserDetails) {
                SupabaseUserDetails supabaseUser = (SupabaseUserDetails) authentication.getPrincipal();
                email = supabaseUser.getEmail();
                logger.info("Found Supabase user: {}", email);
            } else {
                logger.warn("Unexpected authentication principal type: {}", 
                    authentication.getPrincipal().getClass().getSimpleName());
                return ResponseEntity.status(401).body(Map.of("error", "認証情報が無効です"));
            }

            // MySQLからユーザー情報を取得
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                logger.warn("User not found in database: {}", email);
                return ResponseEntity.status(404).body(Map.of("error", "ユーザーが見つかりません"));
            }

            // Firebase URLをフィルタリング（Supabase URLのみ許可）
            String profileImagePath = user.getProfileImagePath();
            if (profileImagePath != null && 
                (profileImagePath.contains("firebasestorage.googleapis.com") || 
                 profileImagePath.contains("firebase") || 
                 profileImagePath.contains("storage.googleapis.com"))) {
                logger.warn("Filtering out Firebase Storage URL: {}", profileImagePath);
                profileImagePath = null; // Firebase URLは無効化
            }
            
            // レスポンス用のユーザー情報を作成
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("name", user.getName());
            userInfo.put("email", user.getEmail());
            userInfo.put("profileImagePath", profileImagePath);
            
            // デバッグ：プロフィール画像のURLをログ出力
            logger.info("Successfully retrieved user info for: {}", email);
            logger.info("Final profile image path: {}", profileImagePath);
            
            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            logger.error("Error retrieving current user info", e);
            return ResponseEntity.status(500).body(Map.of("error", "サーバーエラーが発生しました"));
        }
    }
}