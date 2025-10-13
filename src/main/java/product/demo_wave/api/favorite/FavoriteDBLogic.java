package product.demo_wave.api.favorite;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.annotation.CustomRetry;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.FavoriteDemo;
import product.demo_wave.entity.User;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.FavoriteDemoRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class FavoriteDBLogic {

//	private static final Logger logger = Logger.getLogger(FavoriteListDBLogic.class.getSimpleName());

	private final FavoriteDemoRepository favoriteDemoRepository;
	private final DemoRepository demoRepository;
	private final UserRepository userRepository;

	/**
	 *
	 */
	@CustomRetry
	public FavoriteDemo saveFavorite(String supabaseUid, FavoriteRequest request) {
		User user = fetchUser(supabaseUid);
		Demo demo = fetchDemo(request.getDemoId());

		Optional<FavoriteDemo> existingParticipantOpt = favoriteDemoRepository.findByDemoAndUser(demo, user);

		if (existingParticipantOpt.isPresent()) {
			FavoriteDemo existing = existingParticipantOpt.get();

			if (existing.getDeletedAt() == null) {
				// 登録済み → 不参加として削除
				existing.setDeletedAt(LocalDateTime.now());
				return favoriteDemoRepository.saveAndFlush(existing);
			} else {
				// 論理削除済み → 復活
				existing.setDeletedAt(null);
				return favoriteDemoRepository.saveAndFlush(existing);
			}
		}

		// 未登録 → 新規作成
		FavoriteDemo newFavoriteDemo = new FavoriteDemo();
		newFavoriteDemo.setUser(user);
		newFavoriteDemo.setDemo(demo);

		return favoriteDemoRepository.saveAndFlush(newFavoriteDemo);
	}


	/**
	 * userIdによるお気に入り登録（セッションベース認証用）
	 */
	@CustomRetry
	public FavoriteDemo saveFavoriteByUserId(Integer userId, FavoriteRequest request) {
		User user = fetchUserById(userId);
		Demo demo = fetchDemo(request.getDemoId());

		Optional<FavoriteDemo> existingParticipantOpt = favoriteDemoRepository.findByDemoAndUser(demo, user);

		if (existingParticipantOpt.isPresent()) {
			FavoriteDemo existing = existingParticipantOpt.get();

			if (existing.getDeletedAt() == null) {
				// 登録済み → 不参加として削除
				existing.setDeletedAt(LocalDateTime.now());
				return favoriteDemoRepository.saveAndFlush(existing);
			} else {
				// 論理削除済み → 復活
				existing.setDeletedAt(null);
				return favoriteDemoRepository.saveAndFlush(existing);
			}
		}

		// 未登録 → 新規作成
		FavoriteDemo newFavoriteDemo = new FavoriteDemo();
		newFavoriteDemo.setUser(user);
		newFavoriteDemo.setDemo(demo);

		return favoriteDemoRepository.saveAndFlush(newFavoriteDemo);
	}

	/**
	 * userIdによるお気に入り削除（セッションベース認証用）
	 */
	@CustomRetry
	public void deleteFavoriteByUserId(Integer userId, FavoriteRequest request) {
		User user = fetchUserById(userId);
		Demo demo = fetchDemo(request.getDemoId());

		Optional<FavoriteDemo> existingOpt = favoriteDemoRepository.findByDemoAndUser(demo, user);
		if (existingOpt.isPresent()) {
			FavoriteDemo existing = existingOpt.get();
			existing.setDeletedAt(LocalDateTime.now());
			favoriteDemoRepository.saveAndFlush(existing);
		}
	}

	/**
	 * supabaseUidによるお気に入り削除
	 */
	@CustomRetry
	public void deleteFavorite(String supabaseUid, FavoriteRequest request) {
		User user = fetchUser(supabaseUid);
		Demo demo = fetchDemo(request.getDemoId());

		Optional<FavoriteDemo> existingOpt = favoriteDemoRepository.findByDemoAndUser(demo, user);
		if (existingOpt.isPresent()) {
			FavoriteDemo existing = existingOpt.get();
			existing.setDeletedAt(LocalDateTime.now());
			favoriteDemoRepository.saveAndFlush(existing);
		}
	}

	User fetchUser(String supabaseUid) {
		Optional<User> user = userRepository.findBySupabaseUid(supabaseUid);
		return user.orElse(new User());
	}

	User fetchUserById(Integer userId) {
		Optional<User> user = userRepository.findById(userId);
		return user.orElseThrow(() -> new RuntimeException("User not found"));
	}

	Demo fetchDemo(Integer demoId) {
		Optional<Demo> demo = demoRepository.findById(demoId);
		return demo.orElse(new Demo());
	}

	/**
	 *
	 */
	@CustomRetry
	public Boolean getFavoriteStatus(String supabaseUid, Integer demoId) {
		Boolean isFavorite = favoriteDemoRepository.existsByDemoAndUserAndDeletedAtIsNull(fetchDemo(demoId), fetchUser(supabaseUid));
		return isFavorite;
	}

	/**
	 * userIdによるお気に入り状態取得（セッションベース認証用）
	 */
	@CustomRetry
	public Boolean getFavoriteStatusByUserId(Integer userId, Integer demoId) {
		Boolean isFavorite = favoriteDemoRepository.existsByDemoAndUserAndDeletedAtIsNull(fetchDemo(demoId), fetchUserById(userId));
		return isFavorite;
	}
}
