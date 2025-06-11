package product.demo_wave.api.participation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorResponse;

/**
 * <pre>
 * 参加者かどうかを返す API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/demo/participation-status")
public class ParticipationStatusController {

	private ParticipationStatusService participationStatusService;

	@GetMapping
	public ResponseEntity<APIResponse> responseParticipationStatusStatus(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Firebase トークンを受け取る
			@RequestParam(name = "demoId") Integer demoId
	) {

		// "Bearer <token>" を分離
		String idToken = authorizationHeader.replace("Bearer ", "").trim();

		// Firebase トークンを検証して uid / email を取得
		FirebaseToken decodedToken;
		try {
			decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
		} catch (FirebaseAuthException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Invalid Firebase token"));
		}

		// Firebaseから取得したユーザー情報をリクエストに付加する
		ParticipationStatusContext context = ParticipationStatusContext.builder()
				.firebaseUid(decodedToken.getUid())
				.demoId(demoId)
				.build();

		return participationStatusService.getParticipationStatus(context);
	}

}
