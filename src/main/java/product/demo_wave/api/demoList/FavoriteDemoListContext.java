package product.demo_wave.api.demoList;

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
public class FavoriteDemoListContext {

//	private static final Logger logger = Logger.getLogger(DemoListContext.class.getSimpleName());

	@Getter
	private final String supabaseUid;

	@Setter
	private FavoriteDemoListDBLogic favoriteDemoListDBLogic;

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
	ResponseEntity<APIResponse> getFavoriteDemoList() {
		List<FavoriteDemoListRecord> responses = null;

		responses = favoriteDemoListDBLogic.fetchFavoriteDemoList(supabaseUid);

		return new ResponseEntity<>(new FavoriteDemoListResponse(responses), HttpStatus.OK);
	}

}
