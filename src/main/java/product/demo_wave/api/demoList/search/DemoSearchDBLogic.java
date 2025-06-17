package product.demo_wave.api.demoList.search;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.api.demoList.DemoListRecord;
import product.demo_wave.repository.DemoRepository;

@Component
@AllArgsConstructor
public class DemoSearchDBLogic {

//	private static final Logger logger = Logger.getLogger(DemoListDBLogic.class.getSimpleName());

	private final DemoRepository demoRepository;

	/**
	 * デモ一覧情報のレスポンスを生成します。
	 *
	 * このメソッドは、削除されたユーザーのデータを返しません。
	 * リクエストに存在しないobjが含まれている場合、そのIDに対応するユーザー情報を返しません。
	 *
	 * @return デモ一覧情報のレスポンスを含むリスト
	 */
	List<DemoListRecord> fetchDemoList(Integer prefectureId, LocalDate demoDate, Integer categoryId, String keyword) {

		System.out.println("DB読み込み前まできた。");
		List<DemoListRecord> responses = demoRepository.getDemoSearchList(prefectureId, demoDate, categoryId, keyword);
		return responses;
	}

}
