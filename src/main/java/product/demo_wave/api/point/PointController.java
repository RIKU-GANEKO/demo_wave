package product.demo_wave.api.point;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.demo_wave.common.api.ErrorResponse;
import product.demo_wave.entity.PointBalance;
import product.demo_wave.entity.User;
import product.demo_wave.logic.GetUserLogic;
import product.demo_wave.service.PointService;

/**
 * ポイント関連API
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point")
public class PointController {

	private final PointService pointService;
	private final GetUserLogic getUserLogic;

	@Value("${stripe.key.secret}")
	private String stripeSecretKey;

	@Value("${other.app.base-url}")
	private String baseUrl;

	/**
	 * ポイント残高取得API
	 */
	@GetMapping("/balance")
	public ResponseEntity<?> getBalance() {
		try {
			User user = getUserLogic.getUserFromCache();
			PointBalance balance = pointService.getOrCreatePointBalance(user.getId());

			return ResponseEntity.ok(new PointBalanceResponseDTO(balance.getBalance()));
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("ログインしてください"));
		} catch (Exception e) {
			log.error("ポイント残高取得エラー", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("エラーが発生しました"));
		}
	}

	/**
	 * ポイント購入（Stripe Checkoutセッション作成）
	 */
	@PostMapping("/create-checkout-session")
	public ResponseEntity<?> createCheckoutSession(@RequestBody PointPurchaseRequestDTO request) {
		log.info("ポイント購入リクエスト: price={}, points={}", request.getPrice(), request.getPoints());

		// バリデーション
		if (request.getPrice() == null || request.getPrice() < 50) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("金額は50円以上である必要があります"));
		}
		if (request.getPoints() == null || request.getPoints() <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("ポイント数は1以上である必要があります"));
		}

		try {
			User user = getUserLogic.getUserFromCache();
			Stripe.apiKey = stripeSecretKey;

			// Stripe Checkoutセッション作成
			// PaymentIntentはCheckoutが自動生成し、Webhookで購入レコードを作成する
			SessionCreateParams params = SessionCreateParams.builder()
					.setMode(SessionCreateParams.Mode.PAYMENT)
					.setCustomerEmail(user.getEmail())
					.setSuccessUrl(baseUrl + "/point/purchase?payment_success=true")
					.setCancelUrl(baseUrl + "/point/purchase?payment_cancel=true")
					.addLineItem(SessionCreateParams.LineItem.builder()
							.setQuantity(1L)
							.setPriceData(SessionCreateParams.LineItem.PriceData.builder()
									.setCurrency("jpy")
									.setUnitAmount(request.getPrice().longValue())
									.setProductData(
											SessionCreateParams.LineItem.PriceData.ProductData.builder()
													.setName("DemoWaveポイント " + request.getPoints() + "pt")
													.setDescription("アプリ内で使用できるポイントです")
													.build()
									)
									.build()
							)
							.build()
					)
					// PaymentIntentのメタデータを設定（Webhookで使用）
					.setPaymentIntentData(
							SessionCreateParams.PaymentIntentData.builder()
									.putMetadata("userId", user.getId().toString())
									.putMetadata("userEmail", user.getEmail())
									.putMetadata("points", String.valueOf(request.getPoints()))
									.putMetadata("price", String.valueOf(request.getPrice()))
									.putMetadata("type", "point_purchase")
									.build()
					)
					.build();

			Session session = Session.create(params);
			log.info("Stripe Checkoutセッション作成成功: sessionId={}, paymentIntent={}",
				session.getId(), session.getPaymentIntent());

			return ResponseEntity.ok(new PointCheckoutResponseDTO(session.getUrl()));

		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("ログインしてください"));
		} catch (StripeException e) {
			log.error("Stripeエラー", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("決済処理に失敗しました"));
		} catch (Exception e) {
			log.error("ポイント購入エラー", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("エラーが発生しました"));
		}
	}

	/**
	 * ポイント送付API（デモへの応援）
	 */
	@PostMapping("/send")
	public ResponseEntity<?> sendPoints(@RequestBody PointSendRequestDTO request) {
		log.info("ポイント送付リクエスト: demoId={}, points={}", request.getDemoId(), request.getPoints());

		// バリデーション
		if (request.getDemoId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("デモIDが必要です"));
		}
		if (request.getPoints() == null || request.getPoints() <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("ポイント数は1以上である必要があります"));
		}

		try {
			User user = getUserLogic.getUserFromCache();

			// ポイント送付処理
			pointService.sendPoints(user, request.getDemoId(), request.getPoints());

			log.info("ポイント送付成功: user={}, demo={}, points={}", user.getId(), request.getDemoId(), request.getPoints());
			return ResponseEntity.ok().build();

		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("ログインしてください"));
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(e.getMessage()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse(e.getMessage()));
		} catch (Exception e) {
			log.error("ポイント送付エラー", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("エラーが発生しました"));
		}
	}
}
