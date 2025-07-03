package product.demo_wave.api.demoList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
 * デモ一覧ページ API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/demoList/favorite")
public class FavoriteDemoListController {

	private FavoriteDemoListService favoriteDemoListService;

	/**
	 *
	 * @return デモ一覧情報を含むAPIのレスポンス
	 */
	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<APIResponse> getFavoriteDemoList(
			@RequestHeader(name = "Authorization") String authorizationHeader // ← Firebase トークンを受け取る
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

		FavoriteDemoListContext favoriteDemoListContext = FavoriteDemoListContext.builder()
				.firebaseUid(decodedToken.getUid())
				.build();
		return favoriteDemoListService.getFavoriteDemoList(favoriteDemoListContext);
	}

}
