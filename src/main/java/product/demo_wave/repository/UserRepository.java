package product.demo_wave.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.batch.gift_export.UserEmail;
import product.demo_wave.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  @Query("""
    SELECT new product.demo_wave.batch.gift_export.UserEmail(u.id, u.email)
    FROM User u
    WHERE u.id IN :userIds
  """)
  List<UserEmail> findEmailsByUserIds(@Param("userIds") Set<UUID> userIds);

  // findAllSupabaseUids removed - id is now the Supabase UUID directly
  @Query("SELECT u.id FROM User u")
  List<UUID> findAllUserIds();
}
