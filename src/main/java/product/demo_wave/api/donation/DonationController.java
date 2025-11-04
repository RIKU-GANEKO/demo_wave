package product.demo_wave.api.donation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import product.demo_wave.common.api.ErrorResponse;
import product.demo_wave.entity.User;
import product.demo_wave.logic.GetUserLogic;

/**
 * <pre>
 * 支援金送信用 API（セッションベース認証）
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/payment")
public class DonationController {

	private final DonationService donationService;
	private final GetUserLogic getUserLogic;

	@PostMapping("/create-checkout-session")
	public ResponseEntity<?> createCheckoutSession(@RequestBody DonationRequestDTO request) {
		System.out.println("Received request: " + request);
		System.out.println("Amount: " + request.getAmount());
		System.out.println("Demo ID: " + request.getDemoId());

		// 最低金額50円のバリデーション
		if (request.getAmount() < 50) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("支援金額は50円以上である必要があります"));
		}

		try {
			// Spring Securityセッションからログインユーザーを取得
			User user = getUserLogic.getUserFromCache();
			String supabaseUid = user.getId().toString();
			String userEmail = user.getEmail();
			System.out.println("Logged in user - Supabase UID: " + supabaseUid);
			System.out.println("User email: " + userEmail);

			DonationContext context = DonationContext.builder()
					.supabaseUid(supabaseUid)
					.userEmail(userEmail)
					.request(request)
					.build();

			return donationService.createCheckoutSession(context);
		} catch (UsernameNotFoundException e) {
			System.err.println("User not found in session: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("ログインしてください"));
		} catch (Exception e) {
			System.err.println("General Error: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Internal server error: " + e.getMessage()));
		}
	}

}
