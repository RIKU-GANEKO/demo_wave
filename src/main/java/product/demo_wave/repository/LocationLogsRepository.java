package product.demo_wave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Comment;
import product.demo_wave.entity.LocationLogs;

@Repository
public interface LocationLogsRepository extends JpaRepository<LocationLogs, Integer> {

  List<Comment> findByDemoId(Integer demoId);

}
