package product.demo_wave.api.demoList.location;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorCodeResponse;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.LocationLogs;

/**
 * ユーザ情報取得用 Service
 */
@Builder
public class LocationContext {

//	private static final Logger logger = Logger.getLogger(DemoListContext.class.getSimpleName());

	@Getter
	private final String firebaseUid;
	private final LocationRequest request;

	@Setter
	private LocationDBLogic locationDBLogic;

	private Boolean isWithinRadius;

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
	void checkIsWithinRadius() {

		// ① デモ情報を取得
		Demo demo = locationDBLogic.fetchDemo(request.getDemoId());

		BigDecimal demoLat = demo.getDemoAddressLatitude();
		BigDecimal demoLng = demo.getDemoAddressLongitude();

		// ② 距離を計算（メートル単位）
		double distance = calculateDistance(
				request.getLatitude().doubleValue(),
				request.getLongitude().doubleValue(),
				demoLat.doubleValue(),
				demoLng.doubleValue()
		);

		// ③ 500m以内かを判定
		isWithinRadius = distance <= 500.0;

	}

	// Haversine 公式で距離（メートル）を求める
	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371000; // 地球の半径 (m)

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return R * c; // meters
	}

	/**
	 * レスポンスに含む内容をDBLogicから生成し、APIレスポンスを返す。
	 *
	 * @return APIレスポンスを含むResponseEntityオブジェクト
	 */
	ResponseEntity<APIResponse> postLocation() {

		LocationLogs responses = null;

		responses = locationDBLogic.saveLocationLogs(firebaseUid, request, isWithinRadius);

		return new ResponseEntity<>(new LocationResponse(responses), HttpStatus.OK);
	}

}
