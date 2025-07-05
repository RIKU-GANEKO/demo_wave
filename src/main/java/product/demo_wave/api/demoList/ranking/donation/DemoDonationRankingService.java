package product.demo_wave.api.demoList.ranking.donation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorCode;

/**
 * ユーザ情報取得用 Service
 */
@Service
@AllArgsConstructor
public class DemoDonationRankingService {

//	private static final Logger logger = Logger.getLogger(DemoListService.class.getSimpleName());

	private final DemoDonationRankingDBLogic demoDonationRankingDBLogic;

	/**
	 * @return 検索後のデモ一覧情報に関するレスポンス
	 */
	ResponseEntity<APIResponse> getDemoList(DemoDonationRankingContext demoDonationRankingContext) {
		demoDonationRankingContext.setDemoDonationRankingDBLogic(demoDonationRankingDBLogic);
		try {
			return demoDonationRankingContext.getDemoList();
		}
		catch (Exception e) { // Token認証は突破したが内部的なエラーが起きた場合
			return demoDonationRankingContext.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
