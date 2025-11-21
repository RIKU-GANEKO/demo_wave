package product.demo_wave.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.PointBalance;
import product.demo_wave.entity.PointPurchase;
import product.demo_wave.entity.PointPurchase.PurchaseStatus;
import product.demo_wave.entity.PointTransaction;
import product.demo_wave.entity.User;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.PointBalanceRepository;
import product.demo_wave.repository.PointPurchaseRepository;
import product.demo_wave.repository.PointTransactionRepository;

/**
 * ポイント関連のビジネスロジック
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {

	private final PointBalanceRepository pointBalanceRepository;
	private final PointPurchaseRepository pointPurchaseRepository;
	private final PointTransactionRepository pointTransactionRepository;
	private final DemoRepository demoRepository;
	private final product.demo_wave.repository.UserRepository userRepository;

	/**
	 * ユーザーのポイント残高を取得
	 * 残高レコードが存在しない場合は新規作成
	 */
	@Transactional
	public PointBalance getOrCreatePointBalance(UUID userId) {
		return pointBalanceRepository.findByUserId(userId)
				.orElseGet(() -> {
					PointBalance newBalance = PointBalance.builder()
							.userId(userId)
							.balance(0)
							.build();
					return pointBalanceRepository.save(newBalance);
				});
	}

	/**
	 * ポイント購入履歴を作成（PENDING状態）
	 */
	@Transactional
	public PointPurchase createPurchase(User user, Integer points, BigDecimal price, String stripePaymentIntentId) {
		PointPurchase purchase = PointPurchase.builder()
				.user(user)
				.points(points)
				.price(price)
				.stripePaymentIntentId(stripePaymentIntentId)
				.status(PurchaseStatus.PENDING)
				.build();

		return pointPurchaseRepository.save(purchase);
	}

	/**
	 * ポイント購入完了処理（Stripe Webhook経由で呼び出し）
	 * 決済完了後、ユーザーにポイントを付与
	 */
	@Transactional
	public void completePurchase(String stripePaymentIntentId) {
		PointPurchase purchase = pointPurchaseRepository.findByStripePaymentIntentId(stripePaymentIntentId)
				.orElseThrow(() -> new IllegalArgumentException("Purchase not found for PaymentIntent: " + stripePaymentIntentId));

		// すでに完了済みの場合はスキップ（重複処理防止）
		if (purchase.getStatus() == PurchaseStatus.COMPLETED) {
			log.warn("Purchase already completed: {}", stripePaymentIntentId);
			return;
		}

		// ステータスを完了に更新
		purchase.setStatus(PurchaseStatus.COMPLETED);
		pointPurchaseRepository.save(purchase);

		// ユーザーの残高を更新
		PointBalance balance = getOrCreatePointBalance(purchase.getUser().getId());
		balance.addPoints(purchase.getPoints());
		pointBalanceRepository.save(balance);

		log.info("Point purchase completed. User: {}, Points: {}", purchase.getUser().getId(), purchase.getPoints());
	}

	/**
	 * ポイント購入作成＆完了処理（Webhook用）
	 * 購入レコードを作成して即座に完了状態にし、ポイントを付与
	 */
	@Transactional
	public void createAndCompletePurchase(UUID userId, Integer points, BigDecimal price, String stripePaymentIntentId) {
		// 既存の購入レコードを確認（重複防止）
		var existingPurchase = pointPurchaseRepository.findByStripePaymentIntentId(stripePaymentIntentId);
		if (existingPurchase.isPresent()) {
			log.warn("Purchase already exists for PaymentIntent: {}", stripePaymentIntentId);
			// 既存レコードが存在する場合は完了処理を呼び出し
			completePurchase(stripePaymentIntentId);
			return;
		}

		// Userエンティティの取得
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

		// 購入レコードを作成（COMPLETED状態で）
		PointPurchase purchase = PointPurchase.builder()
				.user(user)
				.points(points)
				.price(price)
				.stripePaymentIntentId(stripePaymentIntentId)
				.status(PurchaseStatus.COMPLETED)
				.build();

		pointPurchaseRepository.save(purchase);

		// ユーザーの残高を更新
		PointBalance balance = getOrCreatePointBalance(userId);
		balance.addPoints(points);
		pointBalanceRepository.save(balance);

		log.info("Point purchase created and completed. User: {}, Points: {}", userId, points);
	}

	/**
	 * ポイント送付処理（デモへの応援）
	 */
	@Transactional
	public PointTransaction sendPoints(User user, Integer demoId, Integer points) {
		// デモの存在確認
		Demo demo = demoRepository.findById(demoId)
				.orElseThrow(() -> new IllegalArgumentException("Demo not found: " + demoId));

		// ポイント残高確認
		PointBalance balance = getOrCreatePointBalance(user.getId());
		if (balance.getBalance() < points) {
			throw new IllegalStateException("ポイント残高が不足しています");
		}

		// ポイント減算
		balance.subtractPoints(points);
		pointBalanceRepository.save(balance);

		// トランザクション記録
		PointTransaction transaction = PointTransaction.builder()
				.user(user)
				.demo(demo)
				.points(points)
				.build();

		return pointTransactionRepository.save(transaction);
	}

	/**
	 * デモの合計応援ポイント数を取得
	 */
	public Integer getTotalPointsByDemoId(Integer demoId) {
		return pointTransactionRepository.sumPointsByDemoId(demoId);
	}
}
