package product.demo_wave.api.user;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;

import product.demo_wave.api.user.emailProviderCreate.EmailUserCreateContext;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorResponse;

/**
 * <pre>
 * ユーザーを新規追加する API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/user/create")
public class UserCreateController {

	private UserCreateService userCreateService;

	@PostMapping
	public ResponseEntity<APIResponse> createUser(
			@RequestHeader(name = "Authorization") String authorizationHeader,
			@RequestBody UserCreateRequest request
	) {
//		// "Bearer <token>" を分離
//		String idTokenString = authorizationHeader.replace("Bearer ", "").trim();
//
//		// GoogleクライアントID（Google Cloud Consoleで設定したもの）
//		String CLIENT_ID = "835299946166-2lhc59i67pr5pi4uukscf2ujfv2rq3kb.apps.googleusercontent.com";
//
//		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
//				new NetHttpTransport(), JacksonFactory.getDefaultInstance())
//				.setAudience(Collections.singletonList(CLIENT_ID))
//				.build();
//
//		GoogleIdToken idToken;
//		try {
//			idToken = verifier.verify(idTokenString);
//		} catch (GeneralSecurityException | IOException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//					.body(new ErrorResponse("Token verification failed"));
//		}
//
//		if (idToken == null) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//					.body(new ErrorResponse("Invalid ID token"));
//		}
//
//		GoogleIdToken.Payload payload = idToken.getPayload();
//
//		String userId = payload.getSubject(); // GoogleのUID
//		String email = payload.getEmail();
//		String name = (String) payload.get("name");
//		String pictureUrl = (String) payload.get("picture");
//
//		System.out.println("userId: " + userId);
//		System.out.println("email: " + email);
//		System.out.println("name: " + name);
//		System.out.println("pictureUrl: " + pictureUrl);

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

		// 登録処理に渡す
		UserCreateContext context = UserCreateContext.builder()
				.firebaseUid(decodedToken.getUid())
				.email(decodedToken.getEmail())
				.request(request)
				.build();

		return userCreateService.postUser(context);
	}

}
