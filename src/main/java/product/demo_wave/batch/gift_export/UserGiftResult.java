package product.demo_wave.batch.gift_export;

import java.math.BigDecimal;
import java.util.UUID;

public class UserGiftResult {
	public UUID userId;
	public String email;
	public BigDecimal amount;

	public UserGiftResult(UUID userId, String email, BigDecimal amount) {
		this.userId = userId;
		this.email = email;
		this.amount = amount;
	}
}
