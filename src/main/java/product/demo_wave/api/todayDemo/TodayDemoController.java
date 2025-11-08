package product.demo_wave.api.todayDemo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorResponse;
import product.demo_wave.security.SupabaseUserDetails;

/**
 * 当日参加予定デモ取得API（Web用）
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/today-demos")
public class TodayDemoController {

	private final TodayDemoService todayDemoService;

	/**
	 * ログインユーザーの当日参加予定デモを取得
	 * Spring Securityのセッション認証を使用
	 *
	 * @param authentication Spring Securityの認証情報
	 * @return 当日参加予定デモのリスト
	 */
	@GetMapping
	public ResponseEntity<APIResponse> getTodayDemos(Authentication authentication) {

		System.out.println("=== Today Demo API Called ===");
		System.out.println("Authentication: " + authentication);
		System.out.println("Is Authenticated: " + (authentication != null && authentication.isAuthenticated()));

		// ログインチェック
		if (authentication == null || !authentication.isAuthenticated()) {
			System.out.println("Not authenticated");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Not logged in"));
		}

		// Spring SecurityのprincipalからSupabase UIDを取得
		SupabaseUserDetails userDetails = (SupabaseUserDetails) authentication.getPrincipal();
		String supabaseUid = userDetails.getUserId();
		System.out.println("Supabase UID: " + supabaseUid);

		try {
			// 当日参加予定デモを取得
			List<TodayDemoDTO> demos = todayDemoService.getTodayDemos(supabaseUid);
			System.out.println("Found " + demos.size() + " demo(s)");
			return ResponseEntity.ok(new TodayDemoResponse(demos));
		} catch (Exception e) {
			System.err.println("Error in getTodayDemos:");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Internal server error: " + e.getMessage()));
		}
	}
}
