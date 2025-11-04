package product.demo_wave.api.donation.webhook;

import org.springframework.beans.factory.annotation.Value;
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
 * Stripe Webhook受信用 API
 * </pre>
 */
@RestController
@RequestMapping("api/payment")
public class WebhookController {

	private final WebhookService webhookService;

	@Value("${stripe.webhook.secret}")
	private String webhookSecret;

	public WebhookController(WebhookService webhookService) {
		this.webhookService = webhookService;
	}

	@PostMapping("/webhook/stripe")
	public ResponseEntity<APIResponse> handleStripeWebhook(
			@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader
	) {
		WebhookContext context = WebhookContext.builder()
				.payload(payload)
				.sigHeader(sigHeader)
				.webhookSecret(webhookSecret)
				.build();

		return webhookService.handleEvent(context);
	}

}
