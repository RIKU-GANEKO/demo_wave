package product.demo_wave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.batch.gift_export.ParticipantEntry;
import product.demo_wave.entity.Comment;
import product.demo_wave.entity.LocationLogs;

@Repository
public interface LocationLogsRepository extends JpaRepository<LocationLogs, Integer> {

  List<Comment> findByDemoId(Integer demoId);

  @Query("""
    SELECT new product.demo_wave.batch.gift_export.ParticipantEntry(l.demo.id, l.user.id)
    FROM LocationLogs l
    WHERE l.isWithinRadius = true AND l.demo.id IN :demoIds
    GROUP BY l.demo.id, l.user.id
  """)
  List<ParticipantEntry> findParticipantsByDemoIds(@Param("demoIds") List<Integer> demoIds);

  /**
   * 特定のユーザーとデモで、当日のチェックイン履歴（500m圏内）が存在するか確認
   */
  @Query("""
    SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END
    FROM LocationLogs l
    WHERE l.user.id = :userId
      AND l.demo.id = :demoId
      AND l.isWithinRadius = true
      AND DATE(l.timestamp) = CURRENT_DATE
  """)
  boolean existsByUserAndDemoAndTodayAndWithinRadius(
      @Param("userId") java.util.UUID userId,
      @Param("demoId") Integer demoId
  );

}
