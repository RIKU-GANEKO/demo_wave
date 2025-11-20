package product.demo_wave.api.donation;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import lombok.Builder;

@Builder
public class DonationContext {

	private final String supabaseUid;
	private final String userEmail;
	private final DonationRequestDTO request;
	private final String stripeSecretKey;
	private final String baseUrl;

	public ResponseEntity<DonationCheckoutResponseDTO> createCheckoutSession() {
		Stripe.apiKey = stripeSecretKey;

		System.out.println("Creating Stripe session for amount: " + request.getAmount());
		System.out.println("Demo ID: " + request.getDemoId());
		System.out.println("Supabase UID: " + supabaseUid);

		try {
			// Payment Intent を明示的に作成してからCheckout Sessionに紐づける
			SessionCreateParams params = SessionCreateParams.builder()
					.setMode(SessionCreateParams.Mode.PAYMENT)
					.setCustomerEmail(userEmail) // ログインユーザーのメールを自動設定
					.setSuccessUrl(baseUrl + "/demo/show?demoId=" + request.getDemoId() + "&payment_success=true")
					.setCancelUrl(baseUrl + "/demo/show?demoId=" + request.getDemoId() + "&payment_cancel=true")
					.addLineItem(SessionCreateParams.LineItem.builder()
							.setQuantity(1L)
							.setPriceData(SessionCreateParams.LineItem.PriceData.builder()
									.setCurrency("jpy")
									.setUnitAmount((long) request.getAmount())
									.setProductData(
											SessionCreateParams.LineItem.PriceData.ProductData.builder()
													.setName("Demo Donation")
													.build()
									)
									.build()
							)
							.build()
					)
					// Payment Intent のメタデータを設定
					.setPaymentIntentData(
							SessionCreateParams.PaymentIntentData.builder()
									.putMetadata("demoId", String.valueOf(request.getDemoId()))
									.putMetadata("supabaseUid", supabaseUid)
									.build()
					)
					.build();

			Session session = Session.create(params);
			System.out.println("Stripe session created successfully: " + session.getId());
			System.out.println("Checkout URL: " + session.getUrl());

			return ResponseEntity.ok(new DonationCheckoutResponseDTO(session.getUrl()));
		} catch (StripeException e) {
			System.err.println("Stripe Error: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Stripeエラー: " + e.getMessage(), e);
		}
	}
}
