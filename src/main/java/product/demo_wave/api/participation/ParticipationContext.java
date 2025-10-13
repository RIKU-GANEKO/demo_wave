package product.demo_wave.api.participation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Setter;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorCodeResponse;
import product.demo_wave.entity.Participant;

/**
 * ユーザ情報取得用 Service
 */
@Builder
public class ParticipationContext {

//	private static final Logger logger = Logger.getLogger(ParticipationListContext.class.getSimpleName());

	private final String supabaseUid;
	private final String email;
	private final ParticipationRequest request;

	@Setter
	private ParticipationDBLogic participationDBLogic;

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
	public ResponseEntity<APIResponse> postParticipation() {
		Participant savedParticipation = participationDBLogic.saveParticipation(supabaseUid, request);
		return new ResponseEntity<>(new ParticipationResponse(savedParticipation), HttpStatus.CREATED);
	}

}
