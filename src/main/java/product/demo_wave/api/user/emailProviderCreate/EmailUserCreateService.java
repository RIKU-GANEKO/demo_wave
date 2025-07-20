package product.demo_wave.api.user.emailProviderCreate;

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
public class EmailUserCreateService {

	//	private static final Logger logger = Logger.getLogger(UserProfileService.class.getSimpleName());

	private final EmailUserCreateDBLogic emailUserCreateDBLogic;

	/**
	 * {@link EmailUserCreateContext}を使用してユーザー作成のレスポンスを返すメソッドです。
	 *
	 * ユーザー追加の処理フローは以下の通りです：
	 * <ol>
	 *     <li>{@link EmailUserCreateDBLogic}を設定します。</li>
	 *     <li>APIキーが正しいかどうかを確認します。</li>
	 *     <li>APIキーが正しければ、デモ一覧情報を取得し、APIレスポンスを生成します。</li>
	 *     <li>APIキーが正しくない場合は401エラーレスポンスを返します。</li>
	 *     <li>その他のエラーが発生した場合は500エラーレスポンスを返します。</li>
	 * </ol>
	 *
	 * @param emailUserCreateContext
	 * @return ユーザー追加に関するレスポンス
	 */
	ResponseEntity<APIResponse> postUser(EmailUserCreateContext emailUserCreateContext) {
		emailUserCreateContext.setEmailUserCreateDBLogic(emailUserCreateDBLogic);
		try {
			return emailUserCreateContext.postUser();
		}
		catch (Exception e) { // 内部的なエラーが起きた場合
			return emailUserCreateContext.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}