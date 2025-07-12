package product.demo_wave.batch.gift_export;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class PaymentSum {
	private Integer demoId;
	private BigDecimal totalAmount;

	public PaymentSum(Integer demoId, BigDecimal totalAmount) {
		this.demoId = demoId;
		this.totalAmount = totalAmount;
	}
}
