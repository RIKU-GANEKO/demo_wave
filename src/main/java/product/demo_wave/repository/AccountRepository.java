package product.demo_wave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	/**
	 * 全件取得（削除されていないもののみ）
	 */
	@Query("SELECT a FROM Account a WHERE a.deletedAt IS NULL")
	List<Account> findAllActiveAccounts();

}
