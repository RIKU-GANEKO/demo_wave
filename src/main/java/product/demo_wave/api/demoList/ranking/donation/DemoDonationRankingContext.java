package product.demo_wave.api.demoList.ranking.donation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorCodeResponse;

/**
 * ユーザ情報取得用 Service
 */
@Builder
public class DemoDonationRankingContext {

//	private static final Logger logger = Logger.getLogger(DemoListContext.class.getSimpleName());

	@Getter
	private final String firebaseUid;
	private final String demoDate;

	@Setter
	private DemoDonationRankingDBLogic demoDonationRankingDBLogic;

	/**
	 * エラーレスポンスを生成して返す。
	 *
	 * @param error エラーの種類に対応する例外オブジェクト
	 * @param errorDescription エラーの詳細
	 * @param httpStatus HTTPステータスコード
	 * @return エラーレスポンスを示すResponseEntityオブジェクト
	 */
	ResponseEntity<APIResponse> errorResponse(String error, String errorDescription, HttpStatus httpStatus) {
		APIResponse errorResponse = new ErrorCodeResponse(error, errorDescription);
		return new ResponseEntity<>(errorResponse, httpStatus);
	}

	/**
	 * レスポンスに含む内容をDBLogicから生成し、APIレスポンスを返す。
	 *
	 * @return APIレスポンスを含むResponseEntityオブジェクト
	 */
	ResponseEntity<APIResponse> getDemoList() {
		List<DemoDonationRankingRecord> responses = null;

		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;

		if (demoDate != null && !demoDate.isEmpty()) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
				YearMonth yearMonth = YearMonth.parse(demoDate, formatter);
				LocalDate startDate = yearMonth.atDay(1);
				LocalDate endDate = yearMonth.atEndOfMonth();
				startDateTime = startDate.atStartOfDay();
				endDateTime = endDate.atTime(23, 59, 59, 999_999_999);
			} catch (Exception e) {
				throw new IllegalArgumentException("日付形式が正しくありません。例: 2025/06", e);
			}
		}

		responses = demoDonationRankingDBLogic.fetchDemoList(startDateTime, endDateTime);

		return new ResponseEntity<>(new DemoDonationRankingResponse(responses), HttpStatus.OK);
	}

}
