package product.demo_wave.api.demoList.search;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/demo/search")
public class DemoSearchController {

	private DemoSearchService demoSearchService;

	/**
	 * @return 検索後のデモ一覧情報を含むAPIのレスポンス
	 */
	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<APIResponse> getDemoList(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Firebase トークンを受け取る
			@RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer prefectureId,
			@RequestParam(required = false) String demoDate,
			@RequestParam(required = false) String keyword
	) {

		System.out.println("リクエストが来た。");

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

		// demoDate を LocalDate に変換
		LocalDate parsedDate = null;
		if (demoDate != null && !demoDate.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			parsedDate = LocalDate.parse(demoDate, formatter);
		}

		DemoSearchContext demoSearchContext = DemoSearchContext.builder()
				.firebaseUid(decodedToken.getUid())
				.categoryId(categoryId)
				.prefectureId(prefectureId)
				.demoDate(parsedDate)
				.keyword(keyword)
				.build();
		return demoSearchService.getDemoList(demoSearchContext);
	}

}
