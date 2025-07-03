package product.demo_wave.api.favorite;

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
 * 「お気に入り」を新規追加する API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/demo/favorite")
public class FavoriteController {

	private FavoriteService favoriteService;

	@PostMapping
	public ResponseEntity<APIResponse> createFavorite(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Firebase トークンを受け取る
			@RequestBody FavoriteRequest request
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

		// Firebaseから取得したユーザー情報をリクエストに付加する
		FavoriteContext context = FavoriteContext.builder()
				.firebaseUid(decodedToken.getUid())
				.request(request)
				.build();

		return favoriteService.postFavorite(context);
	}

}
