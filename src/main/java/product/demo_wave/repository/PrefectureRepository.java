package product.demo_wave.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Prefecture;

@Repository
public interface PrefectureRepository extends JpaRepository<Prefecture, Integer> {

  Optional<Prefecture> findById(Integer prefectureId);

  Optional<Prefecture> findByName(String name);

}
