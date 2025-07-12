package product.demo_wave.batch.gift_export;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class GiftExportTarget {

	private Integer demoId;
	private BigDecimal totalAmount;
	private List<Integer> participantIds;
	private Map<Integer, String> userEmails;

	// コンストラクタなど必要に応じて追加
	public GiftExportTarget(Integer demoId, BigDecimal totalAmount, List<Integer> participantIds, Map<Integer, String> userEmails) {
		this.demoId = demoId;
		this.totalAmount = totalAmount;
		this.participantIds = participantIds;
		this.userEmails = userEmails;
	}
}
