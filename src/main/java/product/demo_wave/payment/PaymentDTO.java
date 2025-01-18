package product.demo_wave.payment;

import java.math.BigDecimal;

record PaymentDTO(
		Integer informationId,
		Integer donateUserId,
		BigDecimal donatedAmount
) {
}
