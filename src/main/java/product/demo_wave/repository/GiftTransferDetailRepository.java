package product.demo_wave.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.GiftTransferDetail;

@Repository
public interface GiftTransferDetailRepository extends JpaRepository<GiftTransferDetail, Integer> {

	/**
	 * 特定ユーザーの受取明細を取得（新しい順）
	 */
	@Query("""
		SELECT d FROM GiftTransferDetail d
		WHERE d.user.id = :userId
		  AND d.deletedAt IS NULL
		ORDER BY d.createdAt DESC
	""")
	List<GiftTransferDetail> findByUserIdOrderByCreatedAtDesc(@Param("userId") UUID userId);

	/**
	 * 特定ユーザーの特定月の受取明細を取得
	 */
	@Query("""
		SELECT d FROM GiftTransferDetail d
		WHERE d.user.id = :userId
		  AND d.giftTransfer.transferMonth = :transferMonth
		  AND d.deletedAt IS NULL
		ORDER BY d.amount DESC
	""")
	List<GiftTransferDetail> findByUserIdAndMonth(
		@Param("userId") UUID userId,
		@Param("transferMonth") java.time.LocalDate transferMonth
	);
}
