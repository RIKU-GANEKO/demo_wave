package product.demo_wave.api.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.annotation.CustomRetry;
import product.demo_wave.entity.User;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class UserCreateDBLogic {

//	private static final Logger logger = Logger.getLogger(UserListDBLogic.class.getSimpleName());

	private final UserRepository userRepository;

	@CustomRetry
	List<UUID> getUserIds() {
		List<UUID> userIds = userRepository.findAllUserIds();
		return userIds;
	}

	/**
	 * ユーザーを保存または更新する
	 * 既存ユーザーの場合は、既存データをマージ（nullでない値のみ更新）
	 */
	@CustomRetry
	User saveUser(String supabaseUid, String email, UserCreateRequest request) {
		UUID userId = UUID.fromString(supabaseUid);

		// 既存ユーザーをチェック
		User existingUser = userRepository.findById(userId).orElse(null);

		if (existingUser != null) {
			// 既存ユーザーの場合はマージ処理
			return mergeUser(existingUser, email, request);
		} else {
			// 新規ユーザーの場合
			User newUser = toEntity(supabaseUid, email, request);
			return userRepository.saveAndFlush(newUser);
		}
	}

	/**
	 * 既存ユーザーのデータをマージ（nullでない値のみ更新）
	 */
	private User mergeUser(User existingUser, String email, UserCreateRequest request) {
		// emailは常に最新に更新
		existingUser.setEmail(email);

		// nameが提供されている場合のみ更新
		if (request.getName() != null && !request.getName().trim().isEmpty()) {
			existingUser.setName(request.getName());
		}

		// profileImagePathが提供されている場合のみ更新
		if (request.getProfileImagePath() != null && !request.getProfileImagePath().trim().isEmpty()) {
			existingUser.setProfileImagePath(request.getProfileImagePath());
		}

		// last_loginを更新
		existingUser.setLastLogin(LocalDateTime.now());

		// created_atは保護（更新しない）
		// updated_atは@UpdateTimestampで自動更新される

		return userRepository.saveAndFlush(existingUser);
	}

	private User toEntity(String supabaseUid, String email, UserCreateRequest request) {

		// Account不要（Stripe + Amazonギフト券使用）

		User user = new User();

		// Supabase UUID を User の ID として設定
		user.setId(UUID.fromString(supabaseUid));
		user.setName(request.getName());
		user.setEmail(email);
		user.setProfileImagePath(request.getProfileImagePath());
		// Supabase manages passwords in auth.users table
		// user.setPassword("123456");
		user.setStatus(true);
		user.setLastLogin(LocalDateTime.now());
		// created_atは@CreationTimestampで自動設定される

		return user;
	}

}
