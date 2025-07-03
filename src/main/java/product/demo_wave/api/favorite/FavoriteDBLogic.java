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
	public FavoriteDemo saveFavorite(String firebaseUid, FavoriteRequest request) {
		User user = fetchUser(firebaseUid);
		Demo demo = fetchDemo(request.getDemoId());

		Optional<FavoriteDemo> existingParticipantOpt = favoriteDemoRepository.findByDemoAndUser(demo, user);

		if (existingParticipantOpt.isPresent()) {
			FavoriteDemo existing = existingParticipantOpt.get();

			if (existing.getDeletedAt() == null) {
				// 登録済み → 不参加として削除
				existing.setDeletedAt(LocalDateTime.now());
				return favoriteDemoRepository.saveAndFlush(existing);
			}
		}

		// 未登録 → 新規作成
		FavoriteDemo newFavoriteDemo = new FavoriteDemo();
		newFavoriteDemo.setUser(user);
		newFavoriteDemo.setDemo(demo);

		return favoriteDemoRepository.saveAndFlush(newFavoriteDemo);
	}


	User fetchUser(String firebaseUid) {
		Optional<User> user = userRepository.findByFirebaseUid(firebaseUid);
		return user.orElse(new User());
	}

	Demo fetchDemo(Integer demoId) {
		Optional<Demo> demo = demoRepository.findById(demoId);
		return demo.orElse(new Demo());
	}

	/**
	 *
	 */
	@CustomRetry
	public Boolean getFavoriteStatus(String firebaseUid, Integer demoId) {
		Boolean isFavorite = favoriteDemoRepository.existsByDemoAndUserAndDeletedAtIsNull(fetchDemo(demoId), fetchUser(firebaseUid));
		return isFavorite;
	}
}
