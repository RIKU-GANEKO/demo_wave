package product.demo_wave.api.user.emailProviderCreate;

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

/**
 * <pre>
 * ユーザーを新規追加する API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/user/create/email")
public class EmailUserCreateController {

	private EmailUserCreateService emailUserCreateService;
	private SupabaseJwtService supabaseJwtService;

	@PostMapping
	public ResponseEntity<APIResponse> createUser(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Supabase トークンを受け取る
			@RequestBody EmailUserRequest request
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

		// Supabaseから取得したユーザー情報をリクエストに付加する
		EmailUserCreateContext context = EmailUserCreateContext.builder()
				.supabaseUid(decodedToken.getUid())
				.email(decodedToken.getEmail())
				.request(request)
				.build();

		ResponseEntity<APIResponse> response = emailUserCreateService.postUser(context);

		// ユーザー作成または更新が成功した場合、Spring SecurityのSecurityContextに認証情報を設定
		// 201 Created（新規ユーザー）または 200 OK（既存ユーザーのマージ）の場合
		if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
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
			System.out.println("✅ Authentication set in SecurityContext for user: " + decodedToken.getEmail());
		}

		return response;
	}

}