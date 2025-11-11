package product.demo_wave.admin.gift;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ギフト配布CSV出力結果DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftExportResultDTO {
	private Boolean success;  // 成功/失敗
	private byte[] csvData;  // CSVデータ（バイト配列）
	private Integer recordCount;  // 処理件数
	private BigDecimal totalAmount;  // 合計金額
	private String errorMessage;  // エラーメッセージ
}
