package product.demo_wave.payment;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import product.demo_wave.common.google.GmailService;
import product.demo_wave.logic.GetUserLogic;

@Service
@AllArgsConstructor
public class PaymentService {

	private final PaymentFacadeDBLogic paymentFacadeDBLogic;
	private final GmailService gmailService;
	private final GetUserLogic getUserLogic;

	private final String YOUR_DOMAIN = "http://localhost:8082/demo_wave";

	public String createCheckoutSession(CheckoutSessionContext context, Integer informationId) {

		Integer userId = this.getUserLogic.getUserFromCache().getId();

		try {
			Stripe.apiKey = "sk_test_51QepbEDawq4VaxvjBS5fkldp8CDPzm3Nbj2bgmcq51fiKsd4PMg0aFi9rwMw0UANAAKqWWTADwgy4EU1hJ1QJZZM00mZV2ayM2";
			// Stripeのセッション作成パラメータを構築
			SessionCreateParams params = SessionCreateParams.builder()
					.setMode(SessionCreateParams.Mode.PAYMENT)
					.setSuccessUrl(YOUR_DOMAIN + "/payment/success")
					.setCancelUrl(YOUR_DOMAIN + "/payment/cancel")
					.setCustomerEmail("test@gmail.com") // メールアドレスを事前設定
					.addLineItem(
							SessionCreateParams.LineItem.builder()
									.setQuantity(context.getQuantity())
									.setPrice(context.getPriceId()) // Price IDを利用
									.build()
					)
					.putMetadata("informationId", informationId.toString()) // informationIdをメタデータに追加
					.putMetadata("donateUserId", userId.toString()) // userIdをメタデータに追加
					.build();
			System.out.println("params.getMode: " + params.getMode());
			System.out.println("params.getSuccessURL: " + params.getSuccessUrl());
			System.out.println("params.getCancelURL: " + params.getCancelUrl());
			System.out.println("params.getEmail: " + params.getCustomerEmail());
			System.out.println("params.addLineItem.quantity: " + params.getLineItems().get(0).getQuantity());
			System.out.println("params.addLineItem.price: " + params.getLineItems().get(0).getPrice());
//			System.out.println("informationId: " + );
//			System.out.println("donateUserId: " + );

			// Stripeセッションを作成
			Session session = Session.create(params);
			System.out.println("session作成完了");

			// セッションURLを返す
			return session.getUrl();
		} catch (Exception e) {
			e.printStackTrace(); // エラー詳細を表示
			throw new RuntimeException("Failed to create Stripe checkout session", e);
		}
	}

	ResponseEntity<String> handleStripeWebhook(
			PaymentWebhookContext paymentWebhookContext) {
		paymentWebhookContext.setPaymentFacadeDBLogic(paymentFacadeDBLogic);
		paymentWebhookContext.setGmailService(gmailService);

		// セッションデータ取得処理
		try {
			paymentWebhookContext.getSessionData();
		} catch (UnsupportedOperationException e) {
			// 例外が発生した場合もここで終了
			return paymentWebhookContext.getResponseEntity();
		}

		// イベントタイプが不正の場合はここで終了
		if (paymentWebhookContext.getResponseEntity() != null) {
			return paymentWebhookContext.getResponseEntity();
		}

		// DB登録処理を呼び出し
		paymentWebhookContext.insertPaymentDataToDb();
		paymentWebhookContext.sendMail();
		return paymentWebhookContext.getResponseEntity();

//		paymentWebhookContext.getSessionData();
//		paymentWebhookContext.insertPaymentDataToDb();
//		return paymentWebhookContext.getResponseEntity();
	}

}
