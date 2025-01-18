package product.demo_wave.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	// informationId を元に支援金の総額を取得
	@Query("SELECT SUM(p.donateAmount) FROM Payment p WHERE p.informationId = :informationId")
	BigDecimal getTotalDonatedAmountByInformationId(Integer informationId);

}
