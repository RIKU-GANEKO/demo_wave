package product.demo_wave.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.batch.gift_export.PaymentSum;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.Payment;
import product.demo_wave.entity.User;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	// demoId を元に支援金の総額を取得
	@Query("SELECT SUM(p.donateAmount) FROM Payment p WHERE p.demo = :demo")
	BigDecimal getTotalDonatedAmountByDemo(Demo demo);

	// userId を元に今月支援した金額の合計を取得
	@Query("""
        SELECT SUM(p.donateAmount) 
        FROM Payment p 
        WHERE p.user = :user
          AND YEAR(p.createdAt) = YEAR(CURRENT_DATE)
          AND MONTH(p.createdAt) = MONTH(CURRENT_DATE)
    """)
	BigDecimal getTotalDonatedAmountByUserForCurrentMonth(User user);

	@Query("""
    SELECT new product.demo_wave.batch.gift_export.PaymentSum(p.demo.id, SUM(p.donateAmount))
    FROM Payment p
    WHERE p.demo.id IN :demoIds
    GROUP BY p.demo.id
""")
	List<PaymentSum> findTotalAmountsByDemoIds(@Param("demoIds") List<Integer> demoIds);

	// デモの支援者リストを取得（削除されていないもののみ）
	List<Payment> findByDemoAndDeletedAtIsNull(Demo demo);

	// ユーザーが支援したデモのリストを取得（削除されていないもののみ、重複を除く）
	@Query("SELECT DISTINCT p.demo FROM Payment p WHERE p.user = :user AND p.deletedAt IS NULL ORDER BY p.demo.demoStartDate DESC")
	List<Demo> findDistinctDemosByUserAndDeletedAtIsNull(@Param("user") User user);

}
