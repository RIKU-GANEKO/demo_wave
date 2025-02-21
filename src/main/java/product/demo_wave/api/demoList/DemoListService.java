package product.demo_wave.api.demoList;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * ユーザ情報取得用 Service
 */
@Service
@AllArgsConstructor
public class DemoListService {

//	private static final Logger logger = Logger.getLogger(DemoListService.class.getSimpleName());

	private final DemoListDBLogic demoListDBLogic;

	/**
	 * {@link DemoListContext}を使用してデモ一覧情報のレスポンスを返すメソッドです。
	 *
	 * デモ一覧情報の処理フローは以下の通りです：
	 * <ol>
	 *     <li>{@link DemoListDBLogic}を設定します。</li>
	 *     <li>APIキーが正しいかどうかを確認します。</li>
	 *     <li>APIキーが正しければ、デモ一覧情報を取得し、APIレスポンスを生成します。</li>
	 *     <li>APIキーが正しくない場合は401エラーレスポンスを返します。</li>
	 *     <li>その他のエラーが発生した場合は500エラーレスポンスを返します。</li>
	 * </ol>
	 *
	 * @param demoListContext
	 * @return デモ一覧情報に関するレスポンス
	 */
	ResponseEntity<APIResponse> getDemoList(DemoListContext demoListContext) {
		demoListContext.setDemoListDBLogic(demoListDBLogic);
		try {
			demoListContext.checkApiKey();
			return demoListContext.getDemoList();
		}
		catch (DemoListException e) {
//			logger.error("DemoListException: ", e);
			return demoListContext.errorResponse(e.getError(), e.getMessage(), e.getHttpStatus());
		}
		catch (Exception e) {
//			logger.error("エラーが発生。", e);
			return demoListContext.errorResponse(DemoListErrorCode.INTERNAL_SERVER_ERROR.getCode(), DemoListErrorCode.INTERNAL_SERVER_ERROR.getDescription(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
