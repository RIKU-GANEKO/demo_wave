package product.demo_wave.batch.gift_export;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

@Getter
public class GiftExportTarget {

	private Integer demoId;
	private BigDecimal totalAmount;
	private List<UUID> participantIds;
	private Map<UUID, String> userEmails;

	// コンストラクタなど必要に応じて追加
	public GiftExportTarget(Integer demoId, BigDecimal totalAmount, List<UUID> participantIds, Map<UUID, String> userEmails) {
		this.demoId = demoId;
		this.totalAmount = totalAmount;
		this.participantIds = participantIds;
		this.userEmails = userEmails;
	}
}
