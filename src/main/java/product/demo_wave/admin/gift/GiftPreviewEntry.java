package product.demo_wave.admin.gift;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ギフト配布プレビュー明細
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftPreviewEntry {
	private UUID userId;
	private String email;
	private BigDecimal amount;
}
