package product.demo_wave.api.demoList;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.entity.Information;
import product.demo_wave.repository.InformationRepository;

@Component
@AllArgsConstructor
public class DemoListDBLogic {

//	private static final Logger logger = Logger.getLogger(DemoListDBLogic.class.getSimpleName());

	private final InformationRepository informationRepository;

//	private final UserRepository userRepository;

//	private final UserProfileRepository userProfileRepository;

	/**
	 * ユーザー情報のレスポンスを生成します。
	 *
	 * このメソッドは、削除されたユーザーのデータを返しません。
	 * リクエストに存在しないobjが含まれている場合、そのIDに対応するユーザー情報を返しません。
	 *
	 * @return デモ一覧情報のレスポンスを含むリスト
	 */
//	List<DemoListRecord> fetchDemoList() {
	List<Information> fetchDemoList() {
//		List<DemoListRecord> responses = new ArrayList<>();
		List<Information> information = informationRepository.findAll();
//		for (String freepassId : freepassIds) {
//			User user = userRepository.findByFreepassIdAndDeletedFalse(freepassId);
//			DemoListRecord demoListResponse = fetchUserProfile(user);
//			if (demoListResponse != null) {
//				responses.add(demoListResponse);
//			}
//		}
		return information;
//		return responses;
	}

//	private DemoListRecord fetchUserProfile(User user) {
//		DemoListRecord response = null;
//		if (user != null) {
//			Long userId = user.getId();
//			UserProfile userProfile = userProfileRepository.findByUserId(userId);
//			response = DemoListRecord.fromEntity(user, userProfile);
//		}
//		return response;
//	}

}
