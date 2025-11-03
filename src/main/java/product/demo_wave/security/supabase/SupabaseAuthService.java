package product.demo_wave.security.supabase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

/**
 * Supabase Authentication API クライアントサービス
 */
@Service
public class SupabaseAuthService {

    private static final Logger logger = LoggerFactory.getLogger(SupabaseAuthService.class);

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.anon.key}")
    private String supabaseAnonKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Supabase Auth APIでログイン
     *
     * @param email メールアドレス
     * @param password パスワード
     * @return SupabaseAuthResponse（access_token, refresh_token, user情報）
     * @throws SupabaseAuthException 認証失敗時
     */
    public SupabaseAuthResponse signInWithPassword(String email, String password) throws SupabaseAuthException {
        String authUrl = supabaseUrl + "/auth/v1/token?grant_type=password";

        // リクエストヘッダー
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseAnonKey);

        // リクエストボディ
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            logger.debug("Supabase Auth API login attempt for: {}", email);

            ResponseEntity<String> response = restTemplate.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());

                SupabaseAuthResponse authResponse = new SupabaseAuthResponse();
                authResponse.setAccessToken(jsonNode.get("access_token").asText());
                authResponse.setRefreshToken(jsonNode.get("refresh_token").asText());
                authResponse.setExpiresIn(jsonNode.get("expires_in").asLong());
                authResponse.setTokenType(jsonNode.get("token_type").asText());

                // ユーザー情報
                JsonNode userNode = jsonNode.get("user");
                if (userNode != null) {
                    authResponse.setUserId(userNode.get("id").asText());
                    authResponse.setEmail(userNode.get("email").asText());
                }

                logger.info("Supabase authentication successful for: {}", email);
                return authResponse;
            } else {
                throw new SupabaseAuthException("Unexpected response from Supabase Auth API");
            }

        } catch (HttpClientErrorException e) {
            logger.error("Supabase authentication failed for {}: {} - {}",
                email, e.getStatusCode(), e.getResponseBodyAsString());

            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new SupabaseAuthException("Invalid email or password");
            } else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new SupabaseAuthException("Email not confirmed or account disabled");
            } else {
                throw new SupabaseAuthException("Authentication failed: " + e.getMessage());
            }

        } catch (Exception e) {
            logger.error("Error during Supabase authentication", e);
            throw new SupabaseAuthException("Authentication error: " + e.getMessage());
        }
    }

    /**
     * リフレッシュトークンで新しいアクセストークンを取得
     */
    public SupabaseAuthResponse refreshToken(String refreshToken) throws SupabaseAuthException {
        String authUrl = supabaseUrl + "/auth/v1/token?grant_type=refresh_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseAnonKey);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("refresh_token", refreshToken);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());

                SupabaseAuthResponse authResponse = new SupabaseAuthResponse();
                authResponse.setAccessToken(jsonNode.get("access_token").asText());
                authResponse.setRefreshToken(jsonNode.get("refresh_token").asText());
                authResponse.setExpiresIn(jsonNode.get("expires_in").asLong());

                return authResponse;
            }

            throw new SupabaseAuthException("Failed to refresh token");

        } catch (Exception e) {
            logger.error("Error refreshing token", e);
            throw new SupabaseAuthException("Token refresh failed: " + e.getMessage());
        }
    }
}
