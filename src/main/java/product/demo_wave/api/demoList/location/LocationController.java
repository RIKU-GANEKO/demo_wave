package product.demo_wave.api.demoList.location;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
 * ユーザーの位置情報がデモの開催場所の500m以内かを判断する。
 * モバイル用（トークン認証）とWeb用（セッション認証）の両方に対応
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
			@RequestHeader(name = "Authorization", required = false) String authorizationHeader, // ← Supabase トークン（モバイル用、オプション）
			Authentication authentication, // ← Spring Security認証（Web用）
			@RequestBody LocationRequest request
	) {

		String supabaseUid;

		// 認証方式を判定
		if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
			// モバイル用：トークン認証
			String idToken = authorizationHeader.replace("Bearer ", "").trim();

			SupabaseToken decodedToken;
			try {
				decodedToken = supabaseJwtService.verifyToken(idToken);
			} catch (JwtException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ErrorResponse("Invalid Supabase token"));
			}
			supabaseUid = decodedToken.getUid();

		} else if (authentication != null && authentication.isAuthenticated()) {
			// Web用：セッション認証
			SupabaseUserDetails userDetails = (SupabaseUserDetails) authentication.getPrincipal();
			supabaseUid = userDetails.getUserId();

		} else {
			// 認証されていない
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Not authenticated"));
		}

		LocationContext locationContext = LocationContext.builder()
				.supabaseUid(supabaseUid)
				.request(request)
				.build();
		return locationService.postLocation(locationContext);
	}

}
