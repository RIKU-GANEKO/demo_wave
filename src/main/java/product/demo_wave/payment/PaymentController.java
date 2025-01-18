package product.demo_wave.payment;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionRetrieveParams;

import lombok.AllArgsConstructor;
import product.demo_wave.logic.GetUserLogic;

@Controller
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/create-checkout-session")
	public ModelAndView createCheckoutSession(
			@RequestParam("informationId") Integer informationId,
			ModelAndView modelAndView,
			RedirectAttributes redirectAttributes) {

		// Contextを生成
		CheckoutSessionContext checkoutSessionContext = new CheckoutSessionContext();

		// Serviceを呼び出してセッション作成
		String checkoutUrl = paymentService.createCheckoutSession(checkoutSessionContext, informationId);

		// 成功時のリダイレクト
		modelAndView.setViewName("redirect:" + checkoutUrl);
		return modelAndView;
	}

	@GetMapping("/success")
	public String success() {
		// success.htmlを返す
		return "success";
	}

	@GetMapping("/cancel")
	public String cancel() {
		// cancel.htmlを返す
		return "cancel";
	}

	@PostMapping("/webhook/stripe")
	public ResponseEntity<String> handleStripeWebhook(
			@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader
	) {
		PaymentWebhookContext paymentWebhookContext = new PaymentWebhookContext(payload, sigHeader);
		return paymentService.handleStripeWebhook(paymentWebhookContext);
	}

}
