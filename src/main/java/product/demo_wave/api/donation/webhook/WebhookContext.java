package product.demo_wave.api.donation.webhook;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import product.demo_wave.common.aws.SESService;

@Builder
@Getter
public class WebhookContext {

	private final String payload;
	private final String sigHeader;
	private final String webhookSecret;

	@Setter
	private WebhookDBLogic webhookDBLogic;

	@Setter
	private SESService sesService;

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
		try {
			Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
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
					String donorEmail = paymentIntentNode.at("/receipt_email").asText(null);

					System.out.println("Raw JSON metadata check:");
					System.out.println("supabaseUid from JSON: " + supabaseUid);
					System.out.println("demoId from JSON: " + demoIdStr);
					System.out.println("amount from JSON: " + amountMinor);
					System.out.println("donorEmail from JSON: " + donorEmail);
					System.out.println("Full metadata node: " + paymentIntentNode.at("/metadata"));

					if (supabaseUid == null || supabaseUid.trim().isEmpty() ||
					    demoIdStr == null || demoIdStr.trim().isEmpty()) {
						System.err.println("❌ メタデータが不足しています");
						System.err.println("Available metadata: " + paymentIntentNode.at("/metadata"));
						throw new IllegalStateException("必要なメタデータがありません: supabaseUid=" + supabaseUid + ", demoId=" + demoIdStr);
					}

					int demoId = Integer.parseInt(demoIdStr);
					BigDecimal amount = BigDecimal.valueOf(amountMinor);

					System.out.println("supabaseUid: " + supabaseUid);
					System.out.println("amount: " + amount);
					System.out.println("demoId: " + demoId);

					webhookDBLogic.saveDonation(supabaseUid, amount, demoId);
					sendMail(donorEmail, amount);

				} else {
					// 通常通りデシリアライズ成功
					paymentIntent = (PaymentIntent) deserializer.getObject().orElseThrow(() ->
							new IllegalStateException("Missing payment intent object"));

					Map<String, String> metadata = paymentIntent.getMetadata();
					String supabaseUid = metadata.get("supabaseUid");
					Integer demoId = Integer.parseInt(metadata.get("demoId"));
					BigDecimal amount = BigDecimal.valueOf(paymentIntent.getAmount());
					String donorEmail = paymentIntent.getReceiptEmail();

					webhookDBLogic.saveDonation(supabaseUid, amount, demoId);
					sendMail(donorEmail, amount);
				}
			} else {
				// 他のイベントタイプの場合はログだけ出して成功を返す
				System.out.println("Ignoring webhook event type: " + event.getType());
				return ResponseEntity.ok(SuccessResponse.of("Webhook received (ignored)"));
			}

			return ResponseEntity.ok(SuccessResponse.of("Webhook received"));

		} catch (SignatureVerificationException e) {
			System.err.println("Stripe署名検証エラー: " + e.getMessage());
			return errorResponse(ErrorCode.INVALID_SIGNATURE.getCode(), "Stripe署名が無効です", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.err.println("Webhook処理エラー: " + e.getMessage());
			return errorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), "Webhookの処理中にエラーが発生しました", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	void sendMail(String recipientEmail, BigDecimal amount) {
		try {
			// 支援者のメールアドレスが取得できない場合はスキップ
			if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
				System.err.println("⚠️ 支援者のメールアドレスが取得できませんでした。メール送信をスキップします。");
				return;
			}

			// SES を使用してメール送信
			sesService.sendEmail(
					recipientEmail,
					"DemoWave 支援金送信完了",
					amount + "円を送金しました！\n\nご支援ありがとうございます。");

			System.out.println("メール送信完了: " + recipientEmail);
		} catch (Exception e) {
			// メール送信関連の例外処理
			System.err.println("メール送信エラー (SES): " + e.getMessage());
		}
	}

}
