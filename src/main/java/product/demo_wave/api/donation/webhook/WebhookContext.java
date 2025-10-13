package product.demo_wave.api.donation.webhook;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.gmail.Gmail;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorCode;
import product.demo_wave.common.api.ErrorCodeResponse;
import product.demo_wave.common.api.SuccessResponse;
import product.demo_wave.common.google.GmailService;

@Builder
@Getter
public class WebhookContext {

	private final String payload;
	private final String sigHeader;

	@Setter
	private WebhookDBLogic webhookDBLogic;

	@Setter
	private GmailService gmailService;

	/**
	 * エラーレスポンスを生成して返す。
	 *
	 * @param error エラーの種類に対応する文字列
	 * @param errorDescription エラーの詳細説明
	 * @param httpStatus HTTPステータスコード
	 * @return エラーレスポンスを含むResponseEntity
	 */
	public ResponseEntity<APIResponse> errorResponse(String error, String errorDescription, HttpStatus httpStatus) {
		APIResponse errorResponse = new ErrorCodeResponse(error, errorDescription);
		return new ResponseEntity<>(errorResponse, httpStatus);
	}

//	FIXME デシリアライズが上手くいっておらず、Jsonを手動パースしてしまっている..
	public ResponseEntity<APIResponse> handleEvent() {
		String endpointSecret = "whsec_18f1e69443ef415e014d356d9789d625a5fb0714d90cbaced686b5e305f5775c";

		try {
			Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
			System.out.println("Received webhook event: " + event.getType());

			if ("payment_intent.succeeded".equals(event.getType())) {
				var deserializer = event.getDataObjectDeserializer();

				PaymentIntent paymentIntent = null;

				if (deserializer.getObject().isEmpty()) {
					// デシリアライズ失敗 → raw JSONを手動でパースする
					System.out.println("❌ デシリアライズに失敗しました → raw JSONでパースします");
					ObjectMapper mapper = new ObjectMapper();
					JsonNode root = mapper.readTree(payload);
					JsonNode paymentIntentNode = root.at("/data/object");

					// 必要な値をJsonNodeから取得
					String supabaseUid = paymentIntentNode.at("/metadata/supabaseUid").asText(null);
					String demoIdStr = paymentIntentNode.at("/metadata/demoId").asText(null);
					long amountMinor = paymentIntentNode.at("/amount").asLong(0);

					System.out.println("Raw JSON metadata check:");
					System.out.println("supabaseUid from JSON: " + supabaseUid);
					System.out.println("demoId from JSON: " + demoIdStr);
					System.out.println("amount from JSON: " + amountMinor);
					System.out.println("Full metadata node: " + paymentIntentNode.at("/metadata"));

					if (supabaseUid == null || supabaseUid.trim().isEmpty() || 
					    demoIdStr == null || demoIdStr.trim().isEmpty()) {
						System.err.println("❌ メタデータが不足しています");
						System.err.println("Available metadata: " + paymentIntentNode.at("/metadata"));
						throw new IllegalStateException("必要なメタデータがありません: supabaseUid=" + supabaseUid + ", demoId=" + demoIdStr);
					}

					int demoId = Integer.parseInt(demoIdStr);
					BigDecimal amount = BigDecimal.valueOf(amountMinor);

					System.out.println("firebaseUid: " + supabaseUid);
					System.out.println("amount: " + amount);
					System.out.println("demoId: " + demoId);

					webhookDBLogic.saveDonation(supabaseUid, amount, demoId);
					sendMail(amount);

				} else {
					// 通常通りデシリアライズ成功
					paymentIntent = (PaymentIntent) deserializer.getObject().orElseThrow(() ->
							new IllegalStateException("Missing payment intent object"));

					Map<String, String> metadata = paymentIntent.getMetadata();
					String supabaseUid = metadata.get("supabaseUid");
					Integer demoId = Integer.parseInt(metadata.get("demoId"));
					BigDecimal amount = BigDecimal.valueOf(paymentIntent.getAmount());

					webhookDBLogic.saveDonation(supabaseUid, amount, demoId);
					sendMail(amount);
				}
			} else {
				// 他のイベントタイプの場合はログだけ出して成功を返す
				System.out.println("Ignoring webhook event type: " + event.getType());
				return ResponseEntity.ok(SuccessResponse.of("Webhook received (ignored)"));
			}

			return ResponseEntity.ok(SuccessResponse.of("Webhook received"));

		} catch (SignatureVerificationException e) {
			return errorResponse(ErrorCode.INVALID_SIGNATURE.getCode(), "Stripe署名が無効です", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return errorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), "Webhookの処理中にエラーが発生しました", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	void sendMail(BigDecimal amount) {
		try {
			// Gmailサービスのインスタンスを取得
			Gmail service = gmailService.getGmailService();

			// メール送信処理
			gmailService.sendEmail(service,
					"uemayorimiyanahakokusai@gmail.com",
					"uemayorimiyanahakokusai@gmail.com", // 本番デプロイ前に支援者Emailアドレスへ修正
					"DemoWave 支援金送信完了",
					amount + "円を送金しました！");

			System.out.println("メール送信完了");
		} catch (IOException | GeneralSecurityException e) {
			// Gmail API関連の例外処理
			System.err.println("メール送信エラー (Gmail API): " + e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
			// メール送信関連の例外処理
			System.err.println("メール送信エラー (Messaging): " + e.getMessage());
			e.printStackTrace();
		}
	}

}
