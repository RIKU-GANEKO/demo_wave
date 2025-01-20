package product.demo_wave.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.gmail.Gmail;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import product.demo_wave.common.google.GmailService;

@RequiredArgsConstructor
class PaymentWebhookContext {

    private final String payload;
    private final String sigHeader;

    @Getter
    private ResponseEntity responseEntity;

    @Setter
    private PaymentFacadeDBLogic paymentFacadeDBLogic;

    @Setter
    private GmailService gmailService;

    private PaymentDTO paymentDTO;

    private BigDecimal donatedAmount;

    void getSessionData() throws UnsupportedOperationException {

        String endpointSecret = "whsec_18f1e69443ef415e014d356d9789d625a5fb0714d90cbaced686b5e305f5775c"; // Webhookシークレット
        System.out.println("endpointSecret: " + endpointSecret);

        try {
            // Webhookイベントの検証
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            // イベントタイプを取得
            String eventType = event.getType();
            System.out.println("eventType: " + eventType);

            // "checkout.session.completed" 以外の場合は処理を終了
            if (!"checkout.session.completed".equals(eventType)) {
                System.out.println("checkout.session.completedでないので終了. ステータス200で返す: " + eventType);
                this.responseEntity = ResponseEntity.ok("checkout.session.completedでないので終了. ステータス200で返す: " + eventType);
                return; // 処理を終了
            }

            if ("checkout.session.completed".equals(eventType)) {
                // Checkoutセッションが完了したときの処理
                System.out.println("checkout.session.completedが来ている");

                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
                StripeObject stripeObject = null;
                if (dataObjectDeserializer.getObject().isPresent()) {
                    System.out.println("存在している");
                    stripeObject = dataObjectDeserializer.getObject().get();
                }
                Session session = (Session) stripeObject;
                //支払い金額や他の情報を取得
                String sessionId = session.getId();
                this.donatedAmount = BigDecimal.valueOf(session.getAmountTotal()); // 総支払額（最小通貨単位）
                String currency = session.getCurrency();
                Integer informationId = Integer.valueOf(session.getMetadata().get("informationId"));
                Integer donateUserId = Integer.valueOf(session.getMetadata().get("donateUserId"));

                System.out.println("支払いが完了しました: Session ID = " + sessionId);
                System.out.println("総支払額: " + this.donatedAmount + " " + currency);
                System.out.println("informationId(デモID) : " + informationId);
                System.out.println("donateUserId : " + donateUserId);

                // 必要な処理（DB保存や通知など）をここで実装
                this.paymentDTO = new PaymentDTO(informationId, donateUserId, donatedAmount);
            }

            this.responseEntity.ok("Webhook received!");
        } catch (SignatureVerificationException e) {
            // Webhook署名の検証エラー
            this.responseEntity.badRequest().body("Invalid signature");
        } catch (Exception e) {
            e.printStackTrace();
            this.responseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook error");
        }
    }

    void insertPaymentDataToDb() throws UnsupportedOperationException {
        paymentFacadeDBLogic.savePayment(this.paymentDTO);
        System.out.println("payment登録完了");
    }

    void sendMail() {
        try {
            // Gmailサービスのインスタンスを取得
            Gmail service = gmailService.getGmailService();
            System.out.println("service発行完了");

            // メール送信処理
            gmailService.sendEmail(service,
                    "uemayorimiyanahakokusai@gmail.com",
                    "uemayorimiyanahakokusai@gmail.com", // 本番デプロイ前に支援者Emailアドレスへ修正
                    "DemoWave 支援金送信完了",
                    this.donatedAmount + "円を送金しました！");

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

//    void setResponseEntity() {
//        this.mv.addObject("information", this.information);
//        this.mv.addObject("isParticipant", this.isParticipant);
//        this.mv.addObject("comments", this.comments);
//        this.mv.setViewName("information");
//    }
}
