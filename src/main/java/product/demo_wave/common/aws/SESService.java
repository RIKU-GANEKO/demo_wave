package product.demo_wave.common.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

/**
 * Amazon SES を使用したメール送信サービス
 */
@Component
public class SESService {

	@Value("${aws.access.key.id}")
	private String awsAccessKeyId;

	@Value("${aws.secret.access.key}")
	private String awsSecretAccessKey;

	@Value("${aws.region}")
	private String awsRegion;

	@Value("${aws.ses.from.email}")
	private String fromEmail;

	/**
	 * SES クライアントを取得
	 */
	private SesClient getSesClient() {
		AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
			awsAccessKeyId,
			awsSecretAccessKey
		);

		return SesClient.builder()
			.region(Region.of(awsRegion))
			.credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
			.build();
	}

	/**
	 * メールを送信（テキストメール）
	 *
	 * @param recipient 送信先メールアドレス
	 * @param subject 件名
	 * @param bodyText 本文（テキスト）
	 */
	public void sendEmail(String recipient, String subject, String bodyText) {
		try (SesClient sesClient = getSesClient()) {
			SendEmailRequest request = SendEmailRequest.builder()
				.source(fromEmail)
				.destination(Destination.builder()
					.toAddresses(recipient)
					.build())
				.message(Message.builder()
					.subject(Content.builder()
						.charset("UTF-8")
						.data(subject)
						.build())
					.body(Body.builder()
						.text(Content.builder()
							.charset("UTF-8")
							.data(bodyText)
							.build())
						.build())
					.build())
				.build();

			sesClient.sendEmail(request);
			System.out.println("Email sent successfully to: " + recipient);

		} catch (SesException e) {
			System.err.println("Failed to send email: " + e.awsErrorDetails().errorMessage());
			throw new RuntimeException("Email sending failed", e);
		}
	}

	/**
	 * メールを送信（HTML メール対応）
	 *
	 * @param recipient 送信先メールアドレス
	 * @param subject 件名
	 * @param bodyText 本文（テキスト）
	 * @param bodyHtml 本文（HTML）
	 */
	public void sendEmail(String recipient, String subject, String bodyText, String bodyHtml) {
		try (SesClient sesClient = getSesClient()) {
			SendEmailRequest request = SendEmailRequest.builder()
				.source(fromEmail)
				.destination(Destination.builder()
					.toAddresses(recipient)
					.build())
				.message(Message.builder()
					.subject(Content.builder()
						.charset("UTF-8")
						.data(subject)
						.build())
					.body(Body.builder()
						.text(Content.builder()
							.charset("UTF-8")
							.data(bodyText)
							.build())
						.html(Content.builder()
							.charset("UTF-8")
							.data(bodyHtml)
							.build())
						.build())
					.build())
				.build();

			sesClient.sendEmail(request);
			System.out.println("Email sent successfully to: " + recipient);

		} catch (SesException e) {
			System.err.println("Failed to send email: " + e.awsErrorDetails().errorMessage());
			throw new RuntimeException("Email sending failed", e);
		}
	}

	/**
	 * 設定済みの送信元メールアドレスを取得
	 */
	public String getFromEmail() {
		return fromEmail;
	}
}
