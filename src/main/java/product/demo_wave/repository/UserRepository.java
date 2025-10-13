package product.demo_wave.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import jp.fb.freepass.hbmanager.common.persistence.entity.Account;
import product.demo_wave.batch.gift_export.UserEmail;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  Optional<User> findById(Integer userId);

  Optional<User> findBySupabaseUid(String supabaseUid);

//  Page<User> findByAccount(Pageable pageable, Account account);

  boolean existsByEmail(String email);

  @Query("""
    SELECT new product.demo_wave.batch.gift_export.UserEmail(u.id, u.email)
    FROM User u
    WHERE u.id IN :userIds
  """)
  List<UserEmail> findEmailsByUserIds(@Param("userIds") Set<Integer> userIds);

  @Query("SELECT u.supabaseUid FROM User u")
  List<String> findAllSupabaseUids();
}
