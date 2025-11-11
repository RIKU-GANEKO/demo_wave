package product.demo_wave.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.GiftTransfer;

@Repository
public interface GiftTransferRepository extends JpaRepository<GiftTransfer, Integer> {

	/**
	 * 特定ユーザーの受取履歴を取得（新しい順）
	 */
	@Query("""
		SELECT g FROM GiftTransfer g
		WHERE g.user.id = :userId
		  AND g.deletedAt IS NULL
		ORDER BY g.transferMonth DESC
	""")
	List<GiftTransfer> findByUserIdOrderByTransferMonthDesc(@Param("userId") UUID userId);
}
