package product.demo_wave.api.user.emailProviderCreate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Setter;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorCodeResponse;
import product.demo_wave.entity.User;

/**
 * ユーザ情報取得用 Service
 */
@Builder
public class EmailUserCreateContext {

	//	private static final Logger logger = Logger.getLogger(UserListContext.class.getSimpleName());

	private final String firebaseUid;
	private final String email;
	private final EmailUserRequest request;

	@Setter
	private EmailUserCreateDBLogic emailUserCreateDBLogic;

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
	public ResponseEntity<APIResponse> postUser() {
		User savedUser = emailUserCreateDBLogic.saveUser(firebaseUid, email, request);
		return new ResponseEntity<>(new EmailUserCreateResponse(savedUser), HttpStatus.CREATED);
	}

}