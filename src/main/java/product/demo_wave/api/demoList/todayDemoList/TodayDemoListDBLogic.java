package product.demo_wave.api.demoList.todayDemoList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.api.demoList.DemoListRecord;
import product.demo_wave.entity.User;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class TodayDemoListDBLogic {

//	private static final Logger logger = Logger.getLogger(DemoListDBLogic.class.getSimpleName());

	private final DemoRepository demoRepository;
	private final UserRepository userRepository;

	/**
	 * デモ一覧情報のレスポンスを生成します。
	 *
	 * このメソッドは、削除されたユーザーのデータを返しません。
	 * リクエストに存在しないobjが含まれている場合、そのIDに対応するユーザー情報を返しません。
	 *
	 * @return デモ一覧情報のレスポンスを含むリスト
	 */
	List<TodayDemoListRecord> fetchTodayDemoList(String firebaseUid) {

		User user = fetchUser(firebaseUid);
		Integer userId = user.getId();

		List<TodayDemoListRecord> responses = demoRepository.getTodayDemoList(userId);
		return responses;
	}

	User fetchUser(String firebaseUid) {
		Optional<User> user = userRepository.findByFirebaseUid(firebaseUid);
		return user.orElse(new User());
	}

}
