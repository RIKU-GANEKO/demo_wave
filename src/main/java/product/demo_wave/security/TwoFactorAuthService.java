package product.demo_wave.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * 二要素認証（Google Authenticator / TOTP）サービス
 * 管理者ログインのセキュリティ強化のため
 */
@Slf4j
@Service
public class TwoFactorAuthService {

    private final GoogleAuthenticator gAuth;

    @Value("${spring.application.name:DemoWave}")
    private String applicationName;

    public TwoFactorAuthService() {
        this.gAuth = new GoogleAuthenticator();
    }

    /**
     * 新しい2FAシークレットキーを生成
     */
    public String generateSecretKey() {
        final GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    /**
     * QRコード用のURLを生成
     *
     * @param email ユーザーのメールアドレス
     * @param secret シークレットキー
     * @return Google Authenticatorで読み込めるotpauth:// URL
     */
    public String getQRCodeUrl(String email, String secret) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(
            applicationName,
            email,
            gAuth.createCredentials(secret)
        );
    }

    /**
     * QRコードをBase64エンコードされた画像として生成
     *
     * @param email ユーザーのメールアドレス
     * @param secret シークレットキー
     * @return Base64エンコードされたPNG画像
     */
    public String generateQRCodeImage(String email, String secret) throws WriterException, IOException {
        String qrCodeUrl = getQRCodeUrl(email, secret);

        BitMatrix matrix = new MultiFormatWriter().encode(
            qrCodeUrl,
            BarcodeFormat.QR_CODE,
            300,  // 幅
            300   // 高さ
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * ユーザーが入力したTOTPコードを検証
     *
     * @param secret ユーザーのシークレットキー
     * @param verificationCode ユーザーが入力した6桁のコード
     * @return 検証が成功したらtrue
     */
    public boolean verifyCode(String secret, int verificationCode) {
        try {
            boolean isValid = gAuth.authorize(secret, verificationCode);

            if (isValid) {
                log.info("2FA検証成功");
            } else {
                log.warn("2FA検証失敗: 無効なコード");
            }

            return isValid;
        } catch (Exception e) {
            log.error("2FA検証エラー", e);
            return false;
        }
    }

    /**
     * ユーザーが入力したTOTPコード（文字列）を検証
     */
    public boolean verifyCode(String secret, String verificationCodeStr) {
        try {
            int verificationCode = Integer.parseInt(verificationCodeStr);
            return verifyCode(secret, verificationCode);
        } catch (NumberFormatException e) {
            log.warn("2FA検証失敗: 無効なコード形式 - {}", verificationCodeStr);
            return false;
        }
    }
}
