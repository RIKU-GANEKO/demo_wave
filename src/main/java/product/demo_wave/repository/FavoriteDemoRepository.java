package product.demo_wave.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Demo;
import product.demo_wave.entity.FavoriteDemo;
import product.demo_wave.entity.User;

@Repository
public interface FavoriteDemoRepository extends JpaRepository<FavoriteDemo, Integer> {

	// `demo_id` に基づいて、現在のユーザーが参加しているかどうかをチェック
	boolean existsByDemoAndUserAndDeletedAtIsNull(Demo demo, User user);

	Optional<FavoriteDemo> findByDemoAndUser(Demo demo, User user);

	// お気に入りのデモを取得（開催予定優先、開催日が新しい順）
	@Query("SELECT f FROM FavoriteDemo f WHERE f.user = :user AND f.deletedAt IS NULL " +
	       "ORDER BY CASE WHEN f.demo.demoEndDate >= CURRENT_TIMESTAMP THEN 0 ELSE 1 END, f.demo.demoStartDate DESC")
	List<FavoriteDemo> findAllByUserAndDeletedAtIsNull(@Param("user") User user);

}
