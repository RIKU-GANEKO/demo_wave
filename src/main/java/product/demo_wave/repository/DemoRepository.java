package product.demo_wave.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Demo;
import product.demo_wave.demo.DemoWithParticipantDTO;

@Repository
public interface DemoRepository extends JpaRepository<Demo, Integer> {

  Page<Demo> findByAnnouncementTimeBeforeOrderByAnnouncementTimeDesc(LocalDateTime time, Pageable pageable);

  @Query(value = "SELECT new product.demo_wave.demo.DemoWithParticipantDTO(" +
          "i.id, i.title, i.demoPlace, i.demoDate, COUNT(p.id)) " +
          "FROM Demo i " +
          "LEFT JOIN Participant p ON i.id = p.demo.id AND p.deletedAt IS NULL " + // 修正: p.demo.id
          "WHERE i.deletedAt IS NULL AND i.announcementTime < :time " +
          "GROUP BY i.id, i.title, i.demoPlace, i.demoDate",
          countQuery = "SELECT COUNT(i) " +
                  "FROM Demo i " +
                  "WHERE i.deletedAt IS NULL AND i.announcementTime < :time")
  Page<DemoWithParticipantDTO> findDemoWithParticipantCountsBeforeAnnouncementTime(
          @Param("time") LocalDateTime time,
          Pageable pageable);

  @Query("SELECT i FROM Demo i WHERE i.id IN (SELECT p.demo.id FROM Participant p WHERE p.user.id = :userId)")
  List<Demo> findParticipatedDemoByUserId(@Param("userId") Integer userId);

  Optional<Demo> findById(Integer demoId);

}
