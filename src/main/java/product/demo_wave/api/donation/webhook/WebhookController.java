package product.demo_wave.api.donation.webhook;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import product.demo_wave.common.api.APIResponse;

/**
 * <pre>
 * ユーザーを新規追加する API
 * </pre>
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/payment")
public class WebhookController {

	private WebhookService webhookService;

	@PostMapping("/webhook/stripe")
	public ResponseEntity<APIResponse> handleStripeWebhook(
			@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader
	) {
		WebhookContext context = WebhookContext.builder()
				.payload(payload)
				.sigHeader(sigHeader)
				.build();

		return webhookService.handleEvent(context);
	}

}
