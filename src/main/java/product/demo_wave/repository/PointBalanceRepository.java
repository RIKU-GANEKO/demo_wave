package product.demo_wave.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.PointBalance;

@Repository
public interface PointBalanceRepository extends JpaRepository<PointBalance, UUID> {

	/**
	 * ユーザーIDでポイント残高を取得
	 */
	Optional<PointBalance> findByUserId(UUID userId);
}
