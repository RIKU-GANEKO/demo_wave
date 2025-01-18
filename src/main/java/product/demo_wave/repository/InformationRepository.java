package product.demo_wave.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Information;
import product.demo_wave.information.InformationWithParticipantDTO;

@Repository
public interface InformationRepository extends JpaRepository<Information, Integer> {

  Page<Information> findByAnnouncementTimeBeforeOrderByAnnouncementTimeDesc(LocalDateTime time, Pageable pageable);

  @Query(value = "SELECT new product.demo_wave.information.InformationWithParticipantDTO(" +
          "i.id, i.title, i.demoPlace, i.demoDate, COUNT(p.id)) " +
          "FROM Information i " +
          "LEFT JOIN Participant p ON i.id = p.informationId AND p.deletedAt IS NULL " +
          "WHERE i.deletedAt IS NULL AND i.announcementTime < :time " +
          "GROUP BY i.id, i.title, i.demoPlace, i.demoDate",
          countQuery = "SELECT COUNT(i) " +
                  "FROM Information i " +
                  "WHERE i.deletedAt IS NULL AND i.announcementTime < :time")
  Page<InformationWithParticipantDTO> findInformationWithParticipantCountsBeforeAnnouncementTime(
          @Param("time") LocalDateTime time,
          Pageable pageable);

  @Query("SELECT i FROM Information i WHERE i.id IN (SELECT p.informationId FROM Participant p WHERE p.userId = :userId)")
  List<Information> findParticipatedInformationByUserId(@Param("userId") Integer userId);

}
