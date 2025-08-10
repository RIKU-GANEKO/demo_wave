package product.demo_wave.api.donation;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;
import product.demo_wave.api.donation.webhook.WebhookDBLogic;
import product.demo_wave.common.api.ErrorResponse;
import product.demo_wave.common.api.SuccessResponse;
import product.demo_wave.common.google.GmailService;

/**
 * モック決済用API（シミュレーター環境でのテスト用）
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/payment/mock")
public class MockDonationController {

	private WebhookDBLogic webhookDBLogic;
	private GmailService gmailService;

	@PostMapping("/complete")
	public ResponseEntity<?> completeMockDonation(
			@RequestBody MockDonationRequestDTO request,
			@RequestHeader(name = "Authorization") String authorizationHeader
	) {
		System.out.println("Mock donation received:");
		System.out.println("Amount: " + request.getAmount());
		System.out.println("Demo ID: " + request.getDemoId());
		
		String idToken = authorizationHeader.replace("Bearer ", "").trim();
		System.out.println("Token received: " + (idToken != null && !idToken.isEmpty()));

		try {
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
			String firebaseUid = decodedToken.getUid();
			System.out.println("Firebase UID: " + firebaseUid);
			
			// DB登録をシミュレート
			BigDecimal amount = BigDecimal.valueOf(request.getAmount());
			webhookDBLogic.saveDonation(firebaseUid, amount, request.getDemoId());
			System.out.println("Mock donation saved to DB");
			
			// メール送信をシミュレート
			try {
				gmailService.sendEmail(
					gmailService.getGmailService(),
					"uemayorimiyanahakokusai@gmail.com",
					"uemayorimiyanahakokusai@gmail.com",
					"DemoWave モック支援金送信完了",
					"モック決済で " + amount + "円を送金しました！（テスト環境）"
				);
				System.out.println("Mock donation email sent");
			} catch (Exception emailError) {
				System.err.println("Mock email sending failed: " + emailError.getMessage());
				// メールエラーは無視して続行
			}
			
			return ResponseEntity.ok(SuccessResponse.of("Mock donation completed successfully"));
			
		} catch (FirebaseAuthException e) {
			System.err.println("Firebase Auth Error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Invalid Firebase token: " + e.getMessage()));
		} catch (Exception e) {
			System.err.println("Mock donation error: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Mock donation failed: " + e.getMessage()));
		}
	}
}