package product.demo_wave.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.PointPurchase;

@Repository
public interface PointPurchaseRepository extends JpaRepository<PointPurchase, Integer> {

	/**
	 * ユーザーIDでポイント購入履歴を取得
	 */
	List<PointPurchase> findByUserIdOrderByCreatedAtDesc(UUID userId);

	/**
	 * Stripe PaymentIntentIDで購入履歴を取得
	 */
	Optional<PointPurchase> findByStripePaymentIntentId(String stripePaymentIntentId);
}
