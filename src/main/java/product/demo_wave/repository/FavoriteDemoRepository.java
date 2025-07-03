package product.demo_wave.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Demo;
import product.demo_wave.entity.FavoriteDemo;
import product.demo_wave.entity.User;

@Repository
public interface FavoriteDemoRepository extends JpaRepository<FavoriteDemo, Integer> {

	// `demo_id` に基づいて、現在のユーザーが参加しているかどうかをチェック
	boolean existsByDemoAndUserAndDeletedAtIsNull(Demo demo, User user);

	Optional<FavoriteDemo> findByDemoAndUser(Demo demo, User user);

	List<FavoriteDemo> findAllByUserAndDeletedAtIsNull(User user);

}
