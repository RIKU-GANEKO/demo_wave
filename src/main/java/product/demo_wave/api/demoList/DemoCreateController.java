package product.demo_wave.api.demoList;

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
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorResponse;

/**
 * <pre>
 * デモを新規追加する API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/demo/create")
public class DemoCreateController {

	private DemoCreateService demoCreateService;

	@PostMapping
	public ResponseEntity<APIResponse> createDemo(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Firebase トークンを受け取る
			@RequestBody DemoRequest request
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

		DemoCreateContext demoCreateContext = DemoCreateContext.builder()
				.firebaseUid(decodedToken.getUid())
				.request(request)
				.build();
		return demoCreateService.postDemo(demoCreateContext);
	}

}
