package product.demo_wave.api.commentList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * デモに紐づくコメントをレスポンスとして返す API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("{demoId}/commentList")
public class CommentListController {

	private CommentListService commentListService;
	private SupabaseJwtService supabaseJwtService;

	/**
	 * AuthorizationヘッダーのApiKeyを使用して認証を行い、デモ一覧情報を取得するAPIエンドポイントです。
	 *
	 * エラーパターン:
	 * <ul>
	 *     <li>400: BAD_REQUEST - クライアントリクエスト不正</li>
	 *     <li>401: UNAUTHORIZED - 認証エラー</li>
	 *     <li>500: INTERNAL_SERVER_ERROR - サーバ内部エラー</li>
	 * </ul>
	 *
	 * @param demoId コメントに紐づくデモのid
	 * @return デモ一覧情報を含むAPIのレスポンス
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<APIResponse> getCommentList(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Supabase トークンを受け取る
			@PathVariable Integer demoId
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

		CommentListContext commentListContext = CommentListContext.builder()
				.supabaseUid(decodedToken.getUid())
				.demoId(demoId)
				.build();
		return commentListService.getCommentList(commentListContext);
	}

}
