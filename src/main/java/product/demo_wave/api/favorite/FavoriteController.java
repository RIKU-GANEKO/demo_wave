package product.demo_wave.api.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import product.demo_wave.security.UsersDetails;

/**
 * <pre>
 * 「お気に入り」を新規追加する API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/demo/favorite")
public class FavoriteController {

	private FavoriteService favoriteService;
	private SupabaseJwtService supabaseJwtService;

	@PostMapping
	public ResponseEntity<APIResponse> createFavorite(
			@RequestHeader(name = "Authorization", required = false) String authorizationHeader,
			@RequestBody FavoriteRequest request
	) {

		// セッションベースの認証をチェック
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()
				&& authentication.getPrincipal() instanceof UsersDetails) {
			// 通常のログインユーザー
			UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
			FavoriteContext context = FavoriteContext.builder()
					.userId(userDetails.getAccountId())
					.request(request)
					.build();
			return favoriteService.postFavorite(context);
		}

		// Supabaseトークンによる認証
		if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
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

			// Supabaseから取得したユーザー情報をリクエストに付加する
			FavoriteContext context = FavoriteContext.builder()
					.supabaseUid(decodedToken.getUid())
					.request(request)
					.build();

			return favoriteService.postFavorite(context);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponse("Authentication required"));
	}

	@DeleteMapping
	public ResponseEntity<APIResponse> deleteFavorite(
			@RequestHeader(name = "Authorization", required = false) String authorizationHeader,
			@RequestBody FavoriteRequest request
	) {

		// セッションベースの認証をチェック
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()
				&& authentication.getPrincipal() instanceof UsersDetails) {
			// 通常のログインユーザー
			UsersDetails userDetails = (UsersDetails) authentication.getPrincipal();
			FavoriteContext context = FavoriteContext.builder()
					.userId(userDetails.getAccountId())
					.request(request)
					.build();
			return favoriteService.deleteFavorite(context);
		}

		// Supabaseトークンによる認証
		if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
			String idToken = authorizationHeader.replace("Bearer ", "").trim();

			SupabaseToken decodedToken;
			try {
				decodedToken = supabaseJwtService.verifyToken(idToken);
			} catch (JwtException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ErrorResponse("Invalid Supabase token"));
			}

			FavoriteContext context = FavoriteContext.builder()
					.supabaseUid(decodedToken.getUid())
					.request(request)
					.build();

			return favoriteService.deleteFavorite(context);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponse("Authentication required"));
	}

}
