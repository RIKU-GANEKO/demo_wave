package product.demo_wave.api.commentList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtException;

import lombok.AllArgsConstructor;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorResponse;
import product.demo_wave.security.SupabaseJwtService;
import product.demo_wave.security.SupabaseToken;

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
	private SupabaseJwtService supabaseJwtService;

	@PostMapping
	public ResponseEntity<APIResponse> createComment(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Supabase トークンを受け取る
			@RequestBody CommentRequest request
	) {

		// "Bearer <token>" を分離
		String idToken = authorizationHeader.replace("Bearer ", "").trim();

		// Supabase トークンを検証して uid を取得
		SupabaseToken decodedToken;
		try {
			decodedToken = supabaseJwtService.verifyToken(idToken);
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Invalid Supabase token"));
		}

		CommentCreateContext commentCreateContext = CommentCreateContext.builder()
				.supabaseUid(decodedToken.getUid())
				.request(request)
				.build();
		return commentCreateService.postComment(commentCreateContext);
	}

}
