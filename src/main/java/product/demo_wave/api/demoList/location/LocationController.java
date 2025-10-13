package product.demo_wave.api.demoList.location;

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
 * ユーザーの位置情報がデモの開催場所の500m以内かを判断する。
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

	private LocationService locationService;
	private SupabaseJwtService supabaseJwtService;

	@PostMapping
	public ResponseEntity<APIResponse> createLocation(
			@RequestHeader(name = "Authorization") String authorizationHeader, // ← Supabase トークンを受け取る
			@RequestBody LocationRequest request
	) {

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

		LocationContext locationContext = LocationContext.builder()
				.supabaseUid(decodedToken.getUid())
				.request(request)
				.build();
		return locationService.postLocation(locationContext);
	}

}
