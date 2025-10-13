package product.demo_wave.api.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorResponse;
import product.demo_wave.security.SupabaseJwtService;
import product.demo_wave.security.SupabaseToken;
import product.demo_wave.security.UsersDetails;

/**
 * <pre>
 * 参加者かどうかを返す API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/demo/favorite-status")
public class FavoriteStatusController {

	private FavoriteStatusService favoriteStatusService;
	private SupabaseJwtService supabaseJwtService;

	@GetMapping
	public ResponseEntity<APIResponse> responseFavoriteStatusStatus(
			@RequestHeader(name = "Authorization", required = false) String authorizationHeader,
			@RequestParam(name = "demoId") Integer demoId
	) {

		// セッションベースの認証をチェック
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()
				&& authentication.getPrincipal() instanceof UsersDetails) {
			// 通常のログインユーザー
			UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
			FavoriteStatusContext context = FavoriteStatusContext.builder()
					.userId(userDetails.getAccountId())
					.demoId(demoId)
					.build();
			return favoriteStatusService.getFavoriteStatus(context);
		}

		// Supabaseトークンによる認証
		if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
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

			// Supabaseから取得したユーザー情報をリクエストに付加する
			FavoriteStatusContext context = FavoriteStatusContext.builder()
					.supabaseUid(decodedToken.getUid())
					.demoId(demoId)
					.build();

			return favoriteStatusService.getFavoriteStatus(context);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponse("Authentication required"));
	}

}
