package product.demo_wave.admin.gift;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import product.demo_wave.security.SupabaseUserDetails;

/**
 * 管理画面：ギフトカード配布CSV出力コントローラー
 *
 * セキュリティ：ADMIN ロールを持つユーザーのみアクセス可能
 */
@Controller
@AllArgsConstructor
@RequestMapping("/admin/gift-export")
@PreAuthorize("hasRole('ADMIN')")  // 管理者のみアクセス可能
public class AdminGiftExportController {

	private final AdminGiftExportService adminGiftExportService;

	/**
	 * 管理画面トップ（対象月選択）
	 */
	@GetMapping
	public String showGiftExportPage(Model model) {
		// 過去6ヶ月のリストを表示
		model.addAttribute("availableMonths", generateAvailableMonths());
		return "admin/giftExport";
	}

	/**
	 * プレビュー表示（確認画面）
	 */
	@PostMapping("/preview")
	public String showPreview(@RequestParam String targetMonth, Model model) {
		try {
			GiftExportPreviewDTO preview = adminGiftExportService.getPreview(targetMonth);
			model.addAttribute("preview", preview);
			model.addAttribute("targetMonth", targetMonth);
			return "admin/giftExportPreview";
		} catch (Exception e) {
			model.addAttribute("error", "プレビュー生成中にエラーが発生しました: " + e.getMessage());
			model.addAttribute("availableMonths", generateAvailableMonths());
			return "admin/giftExport";
		}
	}

	/**
	 * CSV出力 + DB登録（実行）
	 */
	@PostMapping("/execute")
	public ResponseEntity<?> executeExport(
			@RequestParam String targetMonth,
			Authentication authentication) {
		try {
			// 管理者IDを取得
			SupabaseUserDetails userDetails = (SupabaseUserDetails) authentication.getPrincipal();
			UUID adminUserId = UUID.fromString(userDetails.getUserId());

			// CSV出力 + DB登録
			GiftExportResultDTO result = adminGiftExportService.exportCsvAndSaveToDb(targetMonth, adminUserId);

			// CSVファイルとしてダウンロード
			String filename = targetMonth.replace("-", "") + "-gift.csv";

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
					.body(result.getCsvData());

		} catch (IllegalStateException e) {
			// 既に登録済みエラー
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		} catch (Exception e) {
			// その他のエラー
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("エラー: " + e.getMessage());
		}
	}

	/**
	 * 過去6ヶ月のリストを生成
	 */
	private java.util.List<String> generateAvailableMonths() {
		java.time.YearMonth current = java.time.YearMonth.now().minusMonths(1);  // 先月から
		java.util.List<String> months = new java.util.ArrayList<>();
		for (int i = 0; i < 6; i++) {
			months.add(current.minusMonths(i).toString());
		}
		return months;
	}
}
