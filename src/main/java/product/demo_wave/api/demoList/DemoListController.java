package product.demo_wave.api.demoList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * <pre>
 * デモ一覧ページ API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/demoList")
public class DemoListController {

	private DemoListService demoListService;

	/**
	 * AuthorizationヘッダーのApiKeyを使用して認証を行い、デモ一覧情報を取得するAPIエンドポイントです。
	 *
	 *
	 * レスポンス例:
	 * <pre>
	 * [
	 *     {
	 *         "sub": "87bAYgSsdzxZGXv-eWEs-8rJ5xQBZzl1yeKk",
	 *         "email": "dup@example.com",
	 *         "name": "都道府県a",
	 *         ...
	 *     },
	 *     ...
	 * ]
	 * </pre>
	 *
	 * エラーパターン:
	 * <ul>
	 *     <li>400: BAD_REQUEST - クライアントリクエスト不正</li>
	 *     <li>401: UNAUTHORIZED - 認証エラー</li>
	 *     <li>500: INTERNAL_SERVER_ERROR - サーバ内部エラー</li>
	 * </ul>
	 *
	 * @param apiKey Authorizationヘッダーから取得したApiKey
	 * @param expectedApiKey 期待されるApiKey
	 * @return デモ一覧情報を含むAPIのレスポンス
	 */
	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<APIResponse> getDemoList(
			@RequestHeader(name = "X-API-Key", required = false) String apiKey,
			@Value("${api.apikey}")  String expectedApiKey)
	{
		DemoListContext demoListContext = DemoListContext.builder()
				.apiKey(apiKey)
				.expectedApiKey(expectedApiKey)
				.build();
		return demoListService.getDemoList(demoListContext);
	}

}
