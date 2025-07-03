package product.demo_wave.api.demoList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.entity.FavoriteDemo;
import product.demo_wave.entity.User;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.FavoriteDemoRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class FavoriteDemoListDBLogic {

//	private static final Logger logger = Logger.getLogger(DemoListDBLogic.class.getSimpleName());

	private final DemoRepository demoRepository;
	private final UserRepository userRepository;
	private final FavoriteDemoRepository favoriteDemoRepository;

	/**
	 * デモ一覧情報のレスポンスを生成します。
	 *
	 * このメソッドは、削除されたユーザーのデータを返しません。
	 * リクエストに存在しないobjが含まれている場合、そのIDに対応するユーザー情報を返しません。
	 *
	 * @return デモ一覧情報のレスポンスを含むリスト
	 */
	List<FavoriteDemoListRecord> fetchFavoriteDemoList(String firebaseUid) {

		User user = fetchUser(firebaseUid);
		List<Integer> favoriteDemoId = fetchFavoriteDemoId(user);

		if (favoriteDemoId.isEmpty()) {
			return Collections.emptyList();
		}

		List<FavoriteDemoListRecord> responses = demoRepository.getFavoriteDemoList(favoriteDemoId);
		return responses;
	}

	User fetchUser(String firebaseUid) {
		Optional<User> user = userRepository.findByFirebaseUid(firebaseUid);
		return user.orElse(new User());
	}

	private List<Integer> fetchFavoriteDemoId(User user) {
		return favoriteDemoRepository.findAllByUserAndDeletedAtIsNull(user).stream()
				.map(favoriteDemo -> favoriteDemo.getDemo().getId())
				.collect(Collectors.toList());
	}

}
