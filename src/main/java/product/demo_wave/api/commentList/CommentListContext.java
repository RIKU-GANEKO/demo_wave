package product.demo_wave.api.commentList;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import product.demo_wave.api.commentList.APIResponse;
import product.demo_wave.api.commentList.CommentListDBLogic;
import product.demo_wave.api.commentList.CommentListErrorCode;
import product.demo_wave.api.commentList.CommentListException;
import product.demo_wave.api.commentList.CommentListResponse;
import product.demo_wave.api.commentList.ErrorCodeResponse;
import product.demo_wave.entity.Comment;

/**
 * ユーザ情報取得用 Service
 */
@Builder
public class CommentListContext {

//	private static final Logger logger = Logger.getLogger(DemoListContext.class.getSimpleName());

	@Getter
	private final Integer demoId;
	private final String apiKey;
	private final String expectedApiKey;

	@Setter
	private CommentListDBLogic commentListDBLogic;

	/**
	 * Headerで入力されたAPIキーが期待されるAPIキーと一致するかどうかを検証
	 *
	 * @return boolean
	 */
	void checkApiKey() {
		if (!expectedApiKey.equals(apiKey)) {
			throw new CommentListException(CommentListErrorCode.UNAUTHORIZED.getCode(), CommentListErrorCode.UNAUTHORIZED.getDescription(), HttpStatus.UNAUTHORIZED);
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
	ResponseEntity<APIResponse> getCommentList() {
		List<CommentListRecord> responses = null;

//		Integer demoId = objListRequestJson.getDemoId();
		responses = commentListDBLogic.fetchCommentList(demoId);

		return new ResponseEntity<>(new CommentListResponse(responses), HttpStatus.OK);
	}

}
