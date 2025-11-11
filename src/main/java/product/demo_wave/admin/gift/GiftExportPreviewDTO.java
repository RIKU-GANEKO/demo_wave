package product.demo_wave.admin.gift;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ギフト配布プレビューDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftExportPreviewDTO {
	private String targetMonth;  // 対象月（例: "2025-01"）
	private Integer totalUsers;  // 対象ユーザー数
	private BigDecimal totalAmount;  // 合計金額
	private List<GiftPreviewEntry> entries;  // ユーザー別明細
}
