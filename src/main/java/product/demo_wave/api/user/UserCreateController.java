package product.demo_wave.api.user;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import product.demo_wave.security.SupabaseUserDetails;

// Google API imports (for Gmail, etc. - NOT for authentication)
// These are still needed for other features

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
	private SupabaseJwtService supabaseJwtService;

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

		// Supabase トークンを検証して uid / email を取得
		SupabaseToken decodedToken;
		try {
			decodedToken = supabaseJwtService.verifyToken(idToken);
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Invalid Supabase token"));
		}

		// 登録処理に渡す
		UserCreateContext context = UserCreateContext.builder()
				.supabaseUid(decodedToken.getUid())
				.email(decodedToken.getEmail())
				.request(request)
				.build();

		ResponseEntity<APIResponse> response = userCreateService.postUser(context);

		// ユーザー作成が成功した場合、Spring SecurityのSecurityContextに認証情報を設定
		if (response.getStatusCode() == HttpStatus.CREATED) {
			// Supabaseユーザー用の簡易UserDetailsを作成
			SupabaseUserDetails supabaseUser = new SupabaseUserDetails(decodedToken.getEmail(), decodedToken.getUid());

			// Spring SecurityのAuthenticationを作成
			UsernamePasswordAuthenticationToken authToken =
				new UsernamePasswordAuthenticationToken(
					supabaseUser,
					null,
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
				);

			// SecurityContextに認証情報を設定
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}

		return response;
	}

}
