package product.demo_wave.api.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Setter;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorCodeResponse;

/**
 * ユーザ情報取得用 Service
 */
@Builder
public class FavoriteStatusContext {

//	private static final Logger logger = Logger.getLogger(FavoriteListContext.class.getSimpleName());

	private final String supabaseUid;
	private final Integer userId;
	private final Integer demoId;

	@Setter
	private FavoriteDBLogic favoriteDBLogic;

	/**
	 * エラーレスポンスを生成して返す。
	 *
	 * @param error エラーの種類に対応する文字列
	 * @param errorDescription エラーの詳細説明
	 * @param httpStatus HTTPステータスコード
	 * @return エラーレスポンスを含むResponseEntity
	 */
	public ResponseEntity<APIResponse> errorResponse(String error, String errorDescription, HttpStatus httpStatus) {
		APIResponse errorResponse = new ErrorCodeResponse(error, errorDescription);
		return new ResponseEntity<>(errorResponse, httpStatus);
	}

	/**
	 * ユーザー作成処理
	 *
	 * @return 成功時のAPIレスポンス
	 */
	public ResponseEntity<APIResponse> getFavoriteStatus() {
		Boolean isFavorite;
		if (userId != null) {
			// セッションベース認証の場合
			isFavorite = favoriteDBLogic.getFavoriteStatusByUserId(userId, demoId);
		} else {
			// Supabaseトークン認証の場合
			isFavorite = favoriteDBLogic.getFavoriteStatus(supabaseUid, demoId);
		}
		return new ResponseEntity<>(new FavoriteStatusResponse(isFavorite), HttpStatus.OK);
	}

}
