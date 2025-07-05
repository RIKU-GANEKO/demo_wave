package product.demo_wave.api.demoList.ranking.participation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.repository.DemoRepository;

@Component
@AllArgsConstructor
public class DemoParticipationRankingDBLogic {

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
	public List<DemoParticipationRankingRecord> fetchDemoList(LocalDateTime startDate, LocalDateTime endDate) {
		System.out.println("DB読み込み前まできた。");
		try {
			List<DemoParticipationRankingRecord> fullList = demoRepository.getTop10DemoParticipationRankingList(startDate, endDate);
			System.out.println("fullList: " + fullList);

			List<DemoParticipationRankingRecord> top10List = fullList.stream().limit(10).toList();
			System.out.println("response: " + top10List);
			return top10List;
		} catch (Exception e) {
			System.err.println("例外発生: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}


}
