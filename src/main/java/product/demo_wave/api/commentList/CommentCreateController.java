package product.demo_wave.api.commentList;

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
 * デモに紐づくコメントを新規追加する API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/{demoId}/comment/create")
public class CommentCreateController {

	private CommentCreateService commentCreateService;

	@PostMapping
	public ResponseEntity<APIResponse> createComment(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Firebase トークンを受け取る
			@RequestBody CommentRequest request
	) {

		// "Bearer <token>" を分離
		String idToken = authorizationHeader.replace("Bearer ", "").trim();

		// Firebase トークンを検証して uid を取得
		FirebaseToken decodedToken;
		try {
			decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
		} catch (FirebaseAuthException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Invalid Firebase token"));
		}

		CommentCreateContext commentCreateContext = CommentCreateContext.builder()
				.firebaseUid(decodedToken.getUid())
				.request(request)
				.build();
		return commentCreateService.postComment(commentCreateContext);
	}

}
