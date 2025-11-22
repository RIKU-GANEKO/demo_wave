package product.demo_wave.contact;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.demo_wave.common.aws.SESService;

/**
 * お問い合わせメール送信サービス
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final SESService sesService;

    @Value("${contact.recipient.email:uemayorimiyanahakokusai@gmail.com}")
    private String recipientEmail;

    /**
     * お問い合わせ内容をメールで送信
     *
     * @param form お問い合わせフォームの内容
     */
    public void sendContactEmail(ContactForm form) {
        String subject = "[Demo Wave お問い合わせ] " + form.getSubject();

        String bodyText = buildEmailBody(form);
        String bodyHtml = buildEmailBodyHtml(form);

        try {
            sesService.sendEmail(recipientEmail, subject, bodyText, bodyHtml);
            log.info("Contact email sent successfully from: {}", form.getEmail());
        } catch (Exception e) {
            log.error("Failed to send contact email from: {}", form.getEmail(), e);
            throw new RuntimeException("メール送信に失敗しました", e);
        }
    }

    /**
     * プレーンテキストのメール本文を構築
     */
    private String buildEmailBody(ContactForm form) {
        StringBuilder sb = new StringBuilder();
        sb.append("Demo Wave お問い合わせフォームからメッセージが届きました。\n\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        sb.append("【お名前】\n").append(form.getName()).append("\n\n");
        sb.append("【メールアドレス】\n").append(form.getEmail()).append("\n\n");
        sb.append("【件名】\n").append(form.getSubject()).append("\n\n");
        sb.append("【本文】\n").append(form.getMessage()).append("\n\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        sb.append("※このメールはDemo Waveのお問い合わせフォームから自動送信されています。\n");
        sb.append("返信は送信者のメールアドレス宛に直接お送りください。");
        return sb.toString();
    }

    /**
     * HTMLのメール本文を構築
     */
    private String buildEmailBodyHtml(ContactForm form) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html><head><meta charset='UTF-8'></head><body style='font-family: sans-serif; line-height: 1.6; color: #333;'>");
        sb.append("<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>");
        sb.append("<h2 style='color: #2563eb; border-bottom: 2px solid #2563eb; padding-bottom: 10px;'>Demo Wave お問い合わせ</h2>");
        sb.append("<p>お問い合わせフォームからメッセージが届きました。</p>");
        sb.append("<table style='width: 100%; border-collapse: collapse; margin: 20px 0;'>");
        sb.append("<tr><th style='text-align: left; padding: 12px; background: #f3f4f6; border: 1px solid #e5e7eb; width: 30%;'>お名前</th>");
        sb.append("<td style='padding: 12px; border: 1px solid #e5e7eb;'>").append(escapeHtml(form.getName())).append("</td></tr>");
        sb.append("<tr><th style='text-align: left; padding: 12px; background: #f3f4f6; border: 1px solid #e5e7eb;'>メールアドレス</th>");
        sb.append("<td style='padding: 12px; border: 1px solid #e5e7eb;'><a href='mailto:").append(escapeHtml(form.getEmail())).append("'>").append(escapeHtml(form.getEmail())).append("</a></td></tr>");
        sb.append("<tr><th style='text-align: left; padding: 12px; background: #f3f4f6; border: 1px solid #e5e7eb;'>件名</th>");
        sb.append("<td style='padding: 12px; border: 1px solid #e5e7eb;'>").append(escapeHtml(form.getSubject())).append("</td></tr>");
        sb.append("<tr><th style='text-align: left; padding: 12px; background: #f3f4f6; border: 1px solid #e5e7eb; vertical-align: top;'>本文</th>");
        sb.append("<td style='padding: 12px; border: 1px solid #e5e7eb; white-space: pre-wrap;'>").append(escapeHtml(form.getMessage())).append("</td></tr>");
        sb.append("</table>");
        sb.append("<p style='color: #6b7280; font-size: 12px; margin-top: 30px;'>※このメールはDemo Waveのお問い合わせフォームから自動送信されています。<br>返信は送信者のメールアドレス宛に直接お送りください。</p>");
        sb.append("</div></body></html>");
        return sb.toString();
    }

    /**
     * HTMLエスケープ
     */
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }
}
