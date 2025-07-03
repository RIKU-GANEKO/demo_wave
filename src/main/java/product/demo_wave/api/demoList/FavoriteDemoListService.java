package product.demo_wave.api.demoList;

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
public class FavoriteDemoListService {

//	private static final Logger logger = Logger.getLogger(DemoListService.class.getSimpleName());

	private final FavoriteDemoListDBLogic favoriteDemoListDBLogic;

	/**
	 * {@link FavoriteDemoListContext}を使用してデモ一覧情報のレスポンスを返すメソッドです。
	 *
	 * デモ一覧情報の処理フローは以下の通りです：
	 * <ol>
	 *     <li>{@link FavoriteDemoListDBLogic}を設定します。</li>
	 *     <li>APIキーが正しいかどうかを確認します。</li>
	 *     <li>APIキーが正しければ、デモ一覧情報を取得し、APIレスポンスを生成します。</li>
	 *     <li>APIキーが正しくない場合は401エラーレスポンスを返します。</li>
	 *     <li>その他のエラーが発生した場合は500エラーレスポンスを返します。</li>
	 * </ol>
	 *
	 * @param favoriteDemoListContext
	 * @return デモ一覧情報に関するレスポンス
	 */
	ResponseEntity<APIResponse> getFavoriteDemoList(FavoriteDemoListContext favoriteDemoListContext) {
		favoriteDemoListContext.setFavoriteDemoListDBLogic(favoriteDemoListDBLogic);
		try {
			return favoriteDemoListContext.getFavoriteDemoList();
		}
		catch (Exception e) { // Token認証は突破したが内部的なエラーが起きた場合
			return favoriteDemoListContext.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
