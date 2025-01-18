package product.demo_wave.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckoutSessionContext {

	@Getter
	private final String priceId = "price_1QhhGuDawq4VaxvjSNHm4ys9";

	@Getter
	private final long quantity = 1L; // 今回は固定値だが、可変にする場合も考慮可能

}
