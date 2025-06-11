package product.demo_wave.api.participation;

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
public class ParticipationStatusService {

//	private static final Logger logger = Logger.getLogger(ParticipationCreateService.class.getSimpleName());

	private final ParticipationDBLogic participationDBLogic;

	/**
	 * {@link ParticipationStatusContext}を使用してユーザー作成のレスポンスを返すメソッドです。
	 *
	 * ユーザー追加の処理フローは以下の通りです：
	 * <ol>
	 *     <li>{@link ParticipationDBLogic}を設定します。</li>
	 *     <li>APIキーが正しいかどうかを確認します。</li>
	 *     <li>APIキーが正しければ、デモ一覧情報を取得し、APIレスポンスを生成します。</li>
	 *     <li>APIキーが正しくない場合は401エラーレスポンスを返します。</li>
	 *     <li>その他のエラーが発生した場合は500エラーレスポンスを返します。</li>
	 * </ol>
	 *
	 * @param participationStatusContext
	 * @return ユーザー追加に関するレスポンス
	 */
	ResponseEntity<APIResponse> getParticipationStatus(ParticipationStatusContext participationStatusContext) {
		participationStatusContext.setParticipationDBLogic(participationDBLogic);
		try {
			return participationStatusContext.getParticipationStatus();
		}
		catch (Exception e) { // 内部的なエラーが起きた場合
			return participationStatusContext.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
