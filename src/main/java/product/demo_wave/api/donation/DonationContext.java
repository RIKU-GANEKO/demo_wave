package product.demo_wave.api.donation;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import lombok.Builder;

@Builder
public class DonationContext {

	private final String firebaseUid;
	private final DonationRequestDTO request;

	public ResponseEntity<DonationResponseDTO> createPayment() {

		Stripe.apiKey = "sk_test_51QepbEDawq4VaxvjBS5fkldp8CDPzm3Nbj2bgmcq51fiKsd4PMg0aFi9rwMw0UANAAKqWWTADwgy4EU1hJ1QJZZM00mZV2ayM2";

		try {
			// ① Customer作成（今回は都度作成）
			Customer customer = Customer.create(new HashMap<>());

			// ② EphemeralKey作成
			Map<String, Object> params = new HashMap<>();
			params.put("customer", customer.getId());
			params.put("stripe-version", "2022-11-15");  // ハイフンに直す

			EphemeralKey ephemeralKey = EphemeralKey.create(params);

			// ③ PaymentIntent作成
			PaymentIntent paymentIntent = PaymentIntent.create(Map.of(
					"amount", request.getAmount(),
					"currency", "jpy",
					"customer", customer.getId(),
					"automatic_payment_methods", Map.of("enabled", true),
					"metadata", Map.of(
							"demoId", String.valueOf(request.getDemoId()),
							"firebaseUid", firebaseUid
					)
			));

			// ④ DTOで返却
			return ResponseEntity.ok(new DonationResponseDTO(
					paymentIntent.getClientSecret(),
					ephemeralKey.getSecret(),
					customer.getId()
			));

		} catch (StripeException e) {
			throw new RuntimeException("Stripeエラー: " + e.getMessage(), e);
		}
	}
}
