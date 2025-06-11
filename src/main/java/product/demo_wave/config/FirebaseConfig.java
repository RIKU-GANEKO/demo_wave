package product.demo_wave.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

import org.springframework.core.io.Resource;

@Configuration
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class FirebaseConfig {

	@Value("classpath:firebase/firebase-adminsdk.json") // ← classpathを使う！
	private Resource serviceAccount;

	@PostConstruct
	public void init() throws IOException {
		System.out.println("serviceAccount exists: " + serviceAccount.exists());
		System.out.println("serviceAccount filename: " + serviceAccount.getFilename());

		if (!serviceAccount.exists()) {
			throw new FileNotFoundException("Firebase service account file not found in classpath");
		}

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
					.build();
			FirebaseApp.initializeApp(options);
			System.out.println("FirebaseApp initialized");
		}
	}
}
