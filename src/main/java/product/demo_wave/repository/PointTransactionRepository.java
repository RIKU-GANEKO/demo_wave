package product.demo_wave.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.PointTransaction;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, Integer> {

	/**
	 * ユーザーIDでポイント送付履歴を取得
	 */
	List<PointTransaction> findByUserIdOrderByCreatedAtDesc(UUID userId);

	/**
	 * デモIDでポイント送付履歴を取得
	 */
	List<PointTransaction> findByDemoIdOrderByCreatedAtDesc(Integer demoId);

	/**
	 * デモIDの合計ポイント数を取得（削除されていないもののみ）
	 */
	@Query("SELECT COALESCE(SUM(pt.points), 0) FROM PointTransaction pt WHERE pt.demo.id = :demoId AND pt.deletedAt IS NULL")
	Integer sumPointsByDemoId(@Param("demoId") Integer demoId);
}
