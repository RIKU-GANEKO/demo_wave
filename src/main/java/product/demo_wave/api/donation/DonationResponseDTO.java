package product.demo_wave.api.donation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DonationResponseDTO {
	private String paymentIntent;
	private String ephemeralKey;
	private String customer;
}
