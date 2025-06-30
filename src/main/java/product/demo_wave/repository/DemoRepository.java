package product.demo_wave.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.api.demoList.DemoListRecord;
import product.demo_wave.api.demoList.todayDemoList.TodayDemoListRecord;
import product.demo_wave.entity.Demo;
import product.demo_wave.demo.DemoWithParticipantDTO;

@Repository
public interface DemoRepository extends JpaRepository<Demo, Integer> {

  @Query(value = "SELECT new product.demo_wave.demo.DemoWithParticipantDTO(" +
          "i.id, i.title, i.demoPlace, i.demoStartDate, COUNT(p.id)) " +
          "FROM Demo i " +
          "LEFT JOIN Participant p ON i.id = p.demo.id AND p.deletedAt IS NULL " + // 修正: p.demo.id
          "WHERE i.deletedAt IS NULL " +
          "GROUP BY i.id, i.title, i.demoPlace, i.demoStartDate",
          countQuery = "SELECT COUNT(i) " +
                  "FROM Demo i " +
                  "WHERE i.deletedAt IS NULL")
  Page<DemoWithParticipantDTO> findDemoWithParticipantCounts(
          @Param("time") LocalDateTime time,
          Pageable pageable);

  @Query("SELECT i FROM Demo i WHERE i.id IN (SELECT p.demo.id FROM Participant p WHERE p.user.id = :userId)")
  List<Demo> findParticipatedDemoByUserId(@Param("userId") Integer userId);

  Optional<Demo> findById(Integer demoId);

  @Query("""
    SELECT new product.demo_wave.api.demoList.DemoListRecord(
        d.id, d.title, d.content, c.imageUrl, d.demoStartDate, d.demoEndDate, d.demoPlace,
        d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath,
        COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0)
    )
    FROM Demo d
    LEFT JOIN d.category c
    LEFT JOIN d.user u
    LEFT JOIN Participant p ON p.demo = d
    LEFT JOIN Payment pay ON pay.demo = d
    GROUP BY d.id, d.title, d.content, c.imageUrl, d.demoStartDate, d.demoEndDate, d.demoPlace,
        d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath
  """)
  List<DemoListRecord> getDemoList();

  @Query("""
    SELECT new product.demo_wave.api.demoList.DemoListRecord(
        d.id, d.title, d.content, c.imageUrl, d.demoStartDate, d.demoEndDate, d.demoPlace,
        d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath,
        COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0)
    )
    FROM Demo d
    LEFT JOIN d.category c
    LEFT JOIN d.user u
    LEFT JOIN Participant p ON p.demo = d
    LEFT JOIN Payment pay ON pay.demo = d
    WHERE (:prefectureId IS NULL OR d.prefecture.id = :prefectureId)
      AND (:categoryId IS NULL OR d.category.id = :categoryId)
      AND (:demoDate IS NULL OR FUNCTION('DATE', d.demoStartDate) = :demoDate)
      AND ((:keyword IS NULL OR :keyword = '') OR d.title LIKE %:keyword% OR d.content LIKE %:keyword%)
    GROUP BY d.id, d.title, d.content, c.imageUrl, d.demoStartDate, d.demoEndDate, d.demoPlace,
             d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath
""")
  List<DemoListRecord> getDemoSearchList(
          @Param("prefectureId") Integer prefectureId,
          @Param("demoDate") LocalDate demoDate,
          @Param("categoryId") Integer categoryId,
          @Param("keyword") String keyword
  );

  @Query("""
  SELECT new product.demo_wave.api.demoList.todayDemoList.TodayDemoListRecord(
    d.id, d.title, d.content, d.demoStartDate, d.demoEndDate, d.demoPlace,
    d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath,
    COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0)
  )
  FROM Demo d
  LEFT JOIN d.user u
  LEFT JOIN Participant p ON p.demo = d
  LEFT JOIN Payment pay ON pay.demo = d
  WHERE p.user.id = :userId
    AND FUNCTION('DATE', d.demoStartDate) = CURRENT_DATE
  GROUP BY d.id, d.title, d.content, d.demoStartDate, d.demoEndDate, d.demoPlace,
    d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath
""")
  List<TodayDemoListRecord> getTodayDemoList(@Param("userId") Integer userId);

}
