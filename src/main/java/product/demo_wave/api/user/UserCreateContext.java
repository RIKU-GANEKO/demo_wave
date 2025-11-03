package product.demo_wave.api.user;

import java.util.List;
import java.util.UUID;

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
public class UserCreateContext {

//	private static final Logger logger = Logger.getLogger(UserListContext.class.getSimpleName());

	private final String supabaseUid;
	private final String email;
//	private final String name;
//	private final String profileImagePath;
	private final UserCreateRequest request;

	@Setter
	private UserCreateDBLogic userCreateDBLogic;

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
	public Boolean checkValidation() {
		List<UUID> userIds = userCreateDBLogic.getUserIds();
		UUID supabaseUuidObj = UUID.fromString(supabaseUid);
		System.out.println("含まれているか: " + userIds.contains(supabaseUuidObj));
		return userIds.contains(supabaseUuidObj);
	}

	/**
	 * ユーザー作成処理
	 *
	 * @return 成功時のAPIレスポンス
	 */
	public ResponseEntity<APIResponse> postUser() {
		User savedUser = userCreateDBLogic.saveUser(supabaseUid, email, request);
		return new ResponseEntity<>(new UserCreateResponse(savedUser), HttpStatus.CREATED);
	}

}
