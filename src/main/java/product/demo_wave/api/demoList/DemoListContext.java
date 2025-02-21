package product.demo_wave.api.demoList;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import product.demo_wave.entity.Information;

/**
 * ユーザ情報取得用 Service
 */
@Builder
public class DemoListContext {

//	private static final Logger logger = Logger.getLogger(DemoListContext.class.getSimpleName());

	@Getter
	private final String apiKey;
	private final String expectedApiKey;

	@Setter
	private DemoListDBLogic demoListDBLogic;

	/**
	 * Headerで入力されたAPIキーが期待されるAPIキーと一致するかどうかを検証
	 *
	 * @return boolean
	 */
	void checkApiKey() {
		if (!expectedApiKey.equals(apiKey)) {
			throw new DemoListException(DemoListErrorCode.UNAUTHORIZED.getCode(), DemoListErrorCode.UNAUTHORIZED.getDescription(), HttpStatus.UNAUTHORIZED);
		}
	}

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
//		List<DemoListRecord> responses = null;
		List<Information> responses = null;

//		if (freepassIds != null && emails != null) {
//			throw new DemoListException(DemoListErrorCode.INVALID_REQUEST.getCode(), DemoListErrorCode.INVALID_REQUEST.getDescription(), HttpStatus.BAD_REQUEST);
//		} else if (freepassIds != null) {
//			responses = demoListDBLogic.fetchUserInfosByFreepassId(freepassIds);
//		} else if (emails != null) {
//			responses = demoListDBLogic.fetchUserInfosByEmail(emails);
//		} else {
//			throw new DemoListException(DemoListErrorCode.INVALID_REQUEST_2.getCode(), DemoListErrorCode.INVALID_REQUEST_2.getDescription(), HttpStatus.BAD_REQUEST);
//		}

		responses = demoListDBLogic.fetchDemoList();

		return new ResponseEntity<>(new DemoListResponse(responses), HttpStatus.OK);
	}

}
