package product.demo_wave.api.commentList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import product.demo_wave.api.commentList.APIResponse;
import product.demo_wave.api.commentList.CommentListContext;
import product.demo_wave.api.commentList.CommentListDBLogic;
import product.demo_wave.api.commentList.CommentListErrorCode;
import product.demo_wave.api.commentList.CommentListException;

/**
 * ユーザ情報取得用 Service
 */
@Service
@AllArgsConstructor
public class CommentListService {

//	private static final Logger logger = Logger.getLogger(DemoListService.class.getSimpleName());

	private final CommentListDBLogic commentListDBLogic;

	/**
	 * {@link CommentListContext}を使用してコメント一覧情報のレスポンスを返すメソッドです。
	 *
	 * コメント一覧情報の処理フローは以下の通りです：
	 * <ol>
	 *     <li>{@link CommentListDBLogic}を設定します。</li>
	 *     <li>APIキーが正しいかどうかを確認します。</li>
	 *     <li>APIキーが正しければ、デモ一覧情報を取得し、APIレスポンスを生成します。</li>
	 *     <li>APIキーが正しくない場合は401エラーレスポンスを返します。</li>
	 *     <li>その他のエラーが発生した場合は500エラーレスポンスを返します。</li>
	 * </ol>
	 *
	 * @param commentListContext
	 * @return コメント一覧情報に関するレスポンス
	 */
	ResponseEntity<APIResponse> getCommentList(CommentListContext commentListContext) {
		commentListContext.setCommentListDBLogic(commentListDBLogic);
		try {
			commentListContext.checkApiKey();
			return commentListContext.getCommentList();
		}
		catch (CommentListException e) { // Token認証に失敗した場合
//			logger.error("DemoListException: ", e);
			return commentListContext.errorResponse(e.getError(), e.getMessage(), e.getHttpStatus());
		}
		catch (Exception e) { // Token認証は突破したが内部的なエラーが起きた場合
//			logger.error("エラーが発生。", e);
			return commentListContext.errorResponse(CommentListErrorCode.INTERNAL_SERVER_ERROR.getCode(), CommentListErrorCode.INTERNAL_SERVER_ERROR.getDescription(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
