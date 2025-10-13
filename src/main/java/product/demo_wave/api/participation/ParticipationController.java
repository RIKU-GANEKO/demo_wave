package product.demo_wave.api.participation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/**
 * <pre>
 * 参加者を新規追加する API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/demo/participate")
public class ParticipationController {

	private ParticipationService participationService;
	private SupabaseJwtService supabaseJwtService;

	@PostMapping
	public ResponseEntity<APIResponse> createParticipation(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Supabase トークンを受け取る
			@RequestBody ParticipationRequest request
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
		ParticipationContext context = ParticipationContext.builder()
				.supabaseUid(decodedToken.getUid())
				.email(decodedToken.getEmail())
				.request(request)
				.build();

		return participationService.postParticipation(context);
	}

}
