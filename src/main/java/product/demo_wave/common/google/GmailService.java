package product.demo_wave.common.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory; // GsonFactory を使用
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class GmailService {
	private static final String APPLICATION_NAME = "DemoWave App - dev";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance(); // GsonFactory を使用
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
	private static final String CREDENTIALS_FILE_PATH = "/Users/rn-618/demo_wave/credentials/client_secret_202179747639-mpk8l4lp7q54p7f8aa86r79fghfhi8cr.apps.googleusercontent.com.json";

	// 認証情報を取得
	private static Credential getCredentials(final HttpTransport HTTP_TRANSPORT) throws IOException {

		FileReader fileReader = new FileReader(CREDENTIALS_FILE_PATH);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, fileReader);

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
//				.setAccessType("offline")
				.build();

		LocalServerReceiver receiver = new LocalServerReceiver.Builder().build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		return credential;
	}

	/**
	 * Gmail Service を取得
	 */
	public Gmail getGmailService() throws IOException, GeneralSecurityException {
		var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		Credential credential = getCredentials(httpTransport);
		System.out.println("credential発行完了");
		System.out.println("credential: " + credential.getAccessToken());

		try {
			return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
//					.setApplicationName(APPLICATION_NAME)
					.build();
		} catch (Exception e) {
			System.out.println("Gmailサービスの構築に失敗しました: " + e.getMessage());
			e.printStackTrace(); // スタックトレースを完全に表示
			throw e; // 必要に応じて再スロー
		}
	}

	/**
	 * メールを送信
	 */
	public void sendEmail(Gmail service, String sender, String recipient, String subject, String bodyText) throws MessagingException, IOException {
		System.out.println("sendEmail開始");
		MimeMessage email = createEmail(sender, recipient, subject, bodyText);
		var encodedEmail = encodeEmail(email);

		service.users().messages().send("me", encodedEmail).execute();
		System.out.println("Email sent successfully.");
	}

	/**
	 * メールの作成
	 */
	private static MimeMessage createEmail(String sender, String recipient, String subject, String bodyText) throws MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);
		email.setFrom(new InternetAddress(sender));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipient));
		email.setSubject(subject);
		email.setText(bodyText);

		return email;
	}

	/**
	 * メールを Base64 エンコード
	 */
	private static com.google.api.services.gmail.model.Message encodeEmail(MimeMessage email)
			throws IOException, MessagingException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		email.writeTo(buffer); // MessagingException をスローする可能性あり
		byte[] rawMessageBytes = buffer.toByteArray();
		String encodedEmail = com.google.api.client.util.Base64.encodeBase64URLSafeString(rawMessageBytes);

		com.google.api.services.gmail.model.Message message = new com.google.api.services.gmail.model.Message();
		message.setRaw(encodedEmail);
		return message;
	}
}

