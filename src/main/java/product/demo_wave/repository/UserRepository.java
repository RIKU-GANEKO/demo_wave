package product.demo_wave.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import jp.fb.freepass.hbmanager.common.persistence.entity.Account;
import product.demo_wave.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

//  Page<User> findByAccount(Pageable pageable, Account account);

  boolean existsByEmail(String email);
}
