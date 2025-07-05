package product.demo_wave.api.demoList.ranking.donation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.repository.DemoRepository;

@Component
@AllArgsConstructor
public class DemoDonationRankingDBLogic {

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
	public List<DemoDonationRankingRecord> fetchDemoList(LocalDateTime startDate, LocalDateTime endDate) {
		System.out.println("DB読み込み前まできた。");
		List<DemoDonationRankingRecord> fullList = demoRepository.getTop10DemoDonationRankingList(startDate, endDate);

		List<DemoDonationRankingRecord> top10List = fullList.stream().limit(10).toList();
		return top10List;
	}


}
