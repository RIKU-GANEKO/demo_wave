package product.demo_wave.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.coobird.thumbnailator.Thumbnails;

/**
 * Supabase Admin API operations
 */
@Service
public class SupabaseService {

    private static final Logger logger = LoggerFactory.getLogger(SupabaseService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.service.role.key}")
    private String serviceRoleKey;

    /**
     * Supabaseにユーザーを作成
     */
    public String createUser(String email, String password, String name) {
        try {
            // Admin API endpoint for creating users
            String endpoint = supabaseUrl + "/auth/v1/admin/users";
            
            // リクエストボディ
            String requestBody = String.format("""
                {
                    "email": "%s",
                    "password": "%s",
                    "user_metadata": {
                        "full_name": "%s"
                    },
                    "email_confirm": true
                }
                """, email, password, name);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + serviceRoleKey)
                    .header("apikey", serviceRoleKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());

            logger.info("Supabase user creation response status: {}", response.statusCode());
            logger.debug("Supabase user creation response body: {}", response.body());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                JsonNode responseJson = objectMapper.readTree(response.body());
                String userId = responseJson.get("id").asText();
                logger.info("Successfully created Supabase user: {} with ID: {}", email, userId);
                return userId;
            } else {
                logger.error("Failed to create Supabase user. Status: {}, Body: {}",
                        response.statusCode(), response.body());
                throw new RuntimeException("Failed to create Supabase user: " + response.body());
            }

        } catch (Exception e) {
            logger.error("Error creating Supabase user for email: {}", email, e);
            throw new RuntimeException("Failed to create Supabase user", e);
        }
    }

    /**
     * Supabaseでユーザーをログインさせてアクセストークンを取得
     */
    public String signInUser(String email, String password) {
        try {
            // Auth API endpoint for signing in
            String endpoint = supabaseUrl + "/auth/v1/token?grant_type=password";

            // リクエストボディ
            String requestBody = String.format("""
                {
                    "email": "%s",
                    "password": "%s"
                }
                """, email, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/json")
                    .header("apikey", serviceRoleKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            logger.info("Supabase sign in response status: {}", response.statusCode());
            logger.debug("Supabase sign in response body: {}", response.body());

            if (response.statusCode() == 200) {
                JsonNode responseJson = objectMapper.readTree(response.body());
                String accessToken = responseJson.get("access_token").asText();
                logger.info("Successfully signed in Supabase user: {}", email);
                return accessToken;
            } else {
                logger.error("Failed to sign in Supabase user. Status: {}, Body: {}",
                        response.statusCode(), response.body());
                throw new RuntimeException("Failed to sign in Supabase user: " + response.body());
            }

        } catch (Exception e) {
            logger.error("Error signing in Supabase user for email: {}", email, e);
            throw new RuntimeException("Failed to sign in Supabase user", e);
        }
    }

    /**
     * Supabaseユーザーを削除
     */
    public void deleteUser(String userId) {
        try {
            String endpoint = supabaseUrl + "/auth/v1/admin/users/" + userId;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Authorization", "Bearer " + serviceRoleKey)
                    .header("apikey", serviceRoleKey)
                    .DELETE()
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                logger.info("Successfully deleted Supabase user: {}", userId);
            } else {
                logger.warn("Failed to delete Supabase user. Status: {}, Body: {}", 
                        response.statusCode(), response.body());
            }

        } catch (Exception e) {
            logger.error("Error deleting Supabase user: {}", userId, e);
        }
    }

    /**
     * プロフィール画像をSupabase Storageにアップロード（圧縮処理付き）
     */
    public String uploadProfileImage(MultipartFile file, String userId) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }

            logger.info("Original file size: {} bytes", file.getSize());

            // 画像を圧縮
            byte[] compressedImage = compressImage(file);
            logger.info("Compressed file size: {} bytes ({}% of original)",
                    compressedImage.length,
                    (compressedImage.length * 100 / file.getSize()));

            // ファイル名を生成（ユーザーID + UUID + 拡張子は常に.jpg）
            String fileName = "profile/" + userId + "_" + UUID.randomUUID().toString() + ".jpg";

            // Supabase Storage API endpoint
            String endpoint = supabaseUrl + "/storage/v1/object/avatars/" + fileName;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Authorization", "Bearer " + serviceRoleKey)
                    .header("apikey", serviceRoleKey)
                    .header("Content-Type", "image/jpeg")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(compressedImage))
                    .timeout(Duration.ofSeconds(60))
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            logger.info("Supabase storage upload response status: {}", response.statusCode());
            logger.debug("Supabase storage upload response body: {}", response.body());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                // 公開URLを構築
                String publicUrl = supabaseUrl + "/storage/v1/object/public/avatars/" + fileName;
                logger.info("Successfully uploaded profile image: {}", publicUrl);
                return publicUrl;
            } else {
                logger.error("Failed to upload profile image. Status: {}, Body: {}",
                        response.statusCode(), response.body());
                throw new RuntimeException("Failed to upload profile image: " + response.body());
            }

        } catch (IOException e) {
            logger.error("Error reading file during profile image upload", e);
            throw new RuntimeException("Failed to read file during upload", e);
        } catch (Exception e) {
            logger.error("Error uploading profile image to Supabase Storage", e);
            throw new RuntimeException("Failed to upload profile image", e);
        }
    }

    /**
     * 画像を圧縮（800x800ピクセル、JPEG品質85%）
     */
    private byte[] compressImage(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 画像を読み込み
            BufferedImage originalImage = ImageIO.read(inputStream);
            if (originalImage == null) {
                throw new IOException("Invalid image format");
            }

            // 800x800ピクセルにリサイズ、JPEG品質85%で圧縮
            Thumbnails.of(originalImage)
                    .size(800, 800)
                    .outputFormat("jpg")
                    .outputQuality(0.85)
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();

        } catch (IOException e) {
            logger.error("Failed to compress image", e);
            throw e;
        }
    }

    /**
     * Supabase Storageからプロフィール画像を削除
     */
    public void deleteProfileImage(String imageUrl) {
        try {
            if (imageUrl == null || !imageUrl.contains("/storage/v1/object/public/avatars/")) {
                return;
            }

            // URLからファイル名を抽出
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/avatars/") + 9);
            String endpoint = supabaseUrl + "/storage/v1/object/avatars/" + fileName;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Authorization", "Bearer " + serviceRoleKey)
                    .header("apikey", serviceRoleKey)
                    .DELETE()
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                logger.info("Successfully deleted profile image: {}", fileName);
            } else {
                logger.warn("Failed to delete profile image. Status: {}, Body: {}", 
                        response.statusCode(), response.body());
            }

        } catch (Exception e) {
            logger.error("Error deleting profile image: {}", imageUrl, e);
        }
    }
}