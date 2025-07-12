package product.demo_wave.batch.gift_export;

import java.math.BigDecimal;

public class UserGiftResult {
	public Integer userId;
	public String email;
	public BigDecimal amount;

	public UserGiftResult(Integer userId, String email, BigDecimal amount) {
		this.userId = userId;
		this.email = email;
		this.amount = amount;
	}
}
