package product.demo_wave.api.commentList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import product.demo_wave.api.commentList.APIResponse;
import product.demo_wave.api.commentList.CommentListContext;
import product.demo_wave.api.commentList.CommentListService;

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
	 * @param apiKey Authorizationヘッダーから取得したApiKey
	 * @param expectedApiKey 期待されるApiKey
	 * @return デモ一覧情報を含むAPIのレスポンス
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<APIResponse> getCommentList(
			@PathVariable Integer demoId,
			@RequestHeader(name = "X-API-Key", required = false) String apiKey,
			@Value("${api.apikey}")  String expectedApiKey)
	{
		CommentListContext commentListContext = CommentListContext.builder()
				.demoId(demoId)
				.apiKey(apiKey)
				.expectedApiKey(expectedApiKey)
				.build();
		return commentListService.getCommentList(commentListContext);
	}

}
