package product.demo_wave.api.demoList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
 * デモ一覧ページ API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/demoList/favorite")
public class FavoriteDemoListController {

	private FavoriteDemoListService favoriteDemoListService;
	private SupabaseJwtService supabaseJwtService;

	/**
	 *
	 * @return デモ一覧情報を含むAPIのレスポンス
	 */
	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<APIResponse> getFavoriteDemoList(
			@RequestHeader(name = "Authorization") String authorizationHeader // ← Supabase トークンを受け取る
	) {

		// "Bearer <token>" を分離
		String idToken = authorizationHeader.replace("Bearer ", "").trim();

		// Supabase トークンを検証して uid / email を取得
		SupabaseToken decodedToken;
		try {
			decodedToken = supabaseJwtService.verifyToken(idToken);
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Invalid Supabase token"));
		}

		FavoriteDemoListContext favoriteDemoListContext = FavoriteDemoListContext.builder()
				.supabaseUid(decodedToken.getUid())
				.build();
		return favoriteDemoListService.getFavoriteDemoList(favoriteDemoListContext);
	}

}
