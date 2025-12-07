package product.demo_wave.security;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * ログイン試行回数を管理するサービス
 * 10回失敗でアカウントをロック（30分間）
 */
@Slf4j
@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPT = 10;
    private static final int LOCK_TIME_MINUTES = 30;

    // メモリベースの試行回数管理（本番環境ではRedisまたはDBを推奨）
    private final ConcurrentHashMap<String, LoginAttempt> attempts = new ConcurrentHashMap<>();

    /**
     * ログイン失敗時の処理
     */
    public void loginFailed(String email) {
        LoginAttempt attempt = attempts.computeIfAbsent(email, k -> new LoginAttempt());
        attempt.incrementAttempts();

        if (attempt.getAttempts() >= MAX_ATTEMPT) {
            attempt.lock();
            log.warn("アカウントがロックされました: email={}, attempts={}", email, attempt.getAttempts());
        } else {
            log.info("ログイン失敗: email={}, attempts={}/{}", email, attempt.getAttempts(), MAX_ATTEMPT);
        }
    }

    /**
     * ログイン成功時の処理（試行回数リセット）
     */
    public void loginSucceeded(String email) {
        attempts.remove(email);
        log.info("ログイン成功: email={}, 試行回数をリセット", email);
    }

    /**
     * アカウントがロックされているかチェック
     */
    public boolean isBlocked(String email) {
        LoginAttempt attempt = attempts.get(email);
        if (attempt == null) {
            return false;
        }

        // ロック時間が経過していれば自動解除
        if (attempt.isLocked() && attempt.isLockExpired(LOCK_TIME_MINUTES)) {
            attempts.remove(email);
            log.info("アカウントロックが自動解除されました: email={}", email);
            return false;
        }

        return attempt.isLocked();
    }

    /**
     * 現在の試行回数を取得
     */
    public int getAttempts(String email) {
        LoginAttempt attempt = attempts.get(email);
        return attempt != null ? attempt.getAttempts() : 0;
    }

    /**
     * ログイン試行情報を保持する内部クラス
     */
    private static class LoginAttempt {
        private int attempts = 0;
        private boolean locked = false;
        private LocalDateTime lockTime;

        public void incrementAttempts() {
            attempts++;
        }

        public int getAttempts() {
            return attempts;
        }

        public void lock() {
            locked = true;
            lockTime = LocalDateTime.now();
        }

        public boolean isLocked() {
            return locked;
        }

        public boolean isLockExpired(int lockTimeMinutes) {
            if (lockTime == null) {
                return true;
            }
            return LocalDateTime.now().isAfter(lockTime.plusMinutes(lockTimeMinutes));
        }
    }
}
