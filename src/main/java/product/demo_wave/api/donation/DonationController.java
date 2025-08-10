package product.demo_wave.api.donation;

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
import product.demo_wave.common.api.ErrorResponse;

/**
 * <pre>
 * ユーザーを新規追加する API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/payment")
public class DonationController {

	private DonationService donationService;

	@PostMapping("/create-checkout-session")
	public ResponseEntity<?> createCheckoutSession(
			@RequestBody DonationRequestDTO request,
			@RequestHeader(name = "Authorization") String authorizationHeader
	) {
		System.out.println("Received request: " + request);
		System.out.println("Amount: " + request.getAmount());
		System.out.println("Demo ID: " + request.getDemoId());
		
		String idToken = authorizationHeader.replace("Bearer ", "").trim();
		System.out.println("Token received: " + (idToken != null && !idToken.isEmpty()));

		try {
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
			System.out.println("Firebase UID: " + decodedToken.getUid());
			
			DonationContext context = DonationContext.builder()
					.firebaseUid(decodedToken.getUid())
					.request(request)
					.build();

			return donationService.createCheckoutSession(context);
		} catch (FirebaseAuthException e) {
			System.err.println("Firebase Auth Error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Invalid Firebase token: " + e.getMessage()));
		} catch (Exception e) {
			System.err.println("General Error: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Internal server error: " + e.getMessage()));
		}
	}

}
