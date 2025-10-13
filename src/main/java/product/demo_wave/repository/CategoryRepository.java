package product.demo_wave.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

  Optional<Category> findById(Integer categoryId);

  Optional<Category> findByName(String name);
}
