package product.demo_wave.payment;

import java.math.BigDecimal;

import product.demo_wave.entity.Demo;
import product.demo_wave.entity.User;

record PaymentDTO(
		Demo demo,
		User donateUser,
		BigDecimal donatedAmount
) {
}
