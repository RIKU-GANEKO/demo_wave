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
	 * {@link EmailUserCreateContext}を使用してユーザー作成・更新のレスポンスを返すメソッドです。
	 *
	 * ユーザー処理フローは以下の通りです：
	 * <ol>
	 *     <li>{@link EmailUserCreateDBLogic}を設定します。</li>
	 *     <li>新規ユーザーの場合は作成、既存ユーザーの場合はマージ処理を行います。</li>
	 *     <li>成功した場合は適切なHTTPステータスを返します（新規: 201 Created、既存: 200 OK）。</li>
	 *     <li>その他のエラーが発生した場合は500エラーレスポンスを返します。</li>
	 * </ol>
	 *
	 * @param emailUserCreateContext
	 * @return ユーザー作成・更新に関するレスポンス
	 */
	ResponseEntity<APIResponse> postUser(EmailUserCreateContext emailUserCreateContext) {
		emailUserCreateContext.setEmailUserCreateDBLogic(emailUserCreateDBLogic);
		try {
			// 既存ユーザーかどうかをチェック（saveUser内でマージ処理される）
			boolean isExistingUser = emailUserCreateContext.isExistingUser();

			// 新規作成またはマージ処理を実行
			ResponseEntity<APIResponse> response = emailUserCreateContext.postUser();

			// 既存ユーザーの場合は200 OKに変更（マージ処理が成功したことを示す）
			if (isExistingUser && response.getStatusCode() == HttpStatus.CREATED) {
				return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
			}

			return response;
		}
		catch (Exception e) { // 内部的なエラーが起きた場合
			System.err.println("Error in postUser: " + e.getMessage());
			e.printStackTrace();
			return emailUserCreateContext.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}