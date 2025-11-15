package product.demo_wave.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.api.demoList.DemoListRecord;
import product.demo_wave.api.demoList.FavoriteDemoListRecord;
import product.demo_wave.api.demoList.ranking.donation.DemoDonationRankingRecord;
import product.demo_wave.api.demoList.ranking.participation.DemoParticipationRankingRecord;
import product.demo_wave.api.demoList.todayDemoList.TodayDemoListRecord;
import product.demo_wave.demo.DemoWithParticipantDTO;
import product.demo_wave.entity.Demo;

@Repository
public interface DemoRepository extends JpaRepository<Demo, Integer> {

  @Query(value = "SELECT new product.demo_wave.demo.DemoWithParticipantDTO(" +
          "d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0), " +
          "d.category.id, d.category.jaName, d.prefecture.name, d.user.name) " +
          "FROM Demo d " +
          "LEFT JOIN Participant p ON d.id = p.demo.id AND p.deletedAt IS NULL " +
          "LEFT JOIN Payment pay ON d.id = pay.demo.id " +
          "WHERE d.deletedAt IS NULL " +
          "GROUP BY d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, d.category.id, d.category.jaName, d.prefecture.name, d.user.name " +
          "ORDER BY d.demoStartDate DESC",
          countQuery = "SELECT COUNT(d) " +
                  "FROM Demo d " +
                  "WHERE d.deletedAt IS NULL")
  Page<DemoWithParticipantDTO> findDemoWithParticipantCounts(
          @Param("time") LocalDateTime time,
          Pageable pageable);

  @Query(value = "SELECT new product.demo_wave.demo.DemoWithParticipantDTO(" +
          "d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0), " +
          "d.category.id, d.category.jaName, d.prefecture.name, d.user.name) " +
          "FROM Demo d " +
          "LEFT JOIN Participant p ON d.id = p.demo.id AND p.deletedAt IS NULL " +
          "LEFT JOIN Payment pay ON d.id = pay.demo.id " +
          "WHERE d.deletedAt IS NULL " +
          "GROUP BY d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, d.category.id, d.category.jaName, d.prefecture.name, d.user.name " +
          "ORDER BY COUNT(DISTINCT p.id) DESC, d.demoStartDate DESC")
  List<DemoWithParticipantDTO> findTopDemosByParticipantCount(Pageable pageable);

  // 開催中・開催予定のデモを優先して取得（参加者数と支援金額を考慮）
  @Query(value = "SELECT new product.demo_wave.demo.DemoWithParticipantDTO(" +
          "d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0), " +
          "d.category.id, d.category.jaName, d.prefecture.name, d.user.name) " +
          "FROM Demo d " +
          "LEFT JOIN Participant p ON d.id = p.demo.id AND p.deletedAt IS NULL " +
          "LEFT JOIN Payment pay ON d.id = pay.demo.id " +
          "WHERE d.deletedAt IS NULL " +
          "GROUP BY d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, d.category.id, d.category.jaName, d.prefecture.name, d.user.name " +
          "ORDER BY " +
          "CASE WHEN d.demoEndDate >= CURRENT_TIMESTAMP THEN 0 ELSE 1 END, " +
          "COUNT(DISTINCT p.id) DESC, " +
          "COALESCE(SUM(pay.donateAmount), 0) DESC, " +
          "d.demoStartDate DESC")
  List<DemoWithParticipantDTO> findDemosByPopularityPrioritizingUpcoming(Pageable pageable);

  @Query("SELECT i FROM Demo i WHERE i.id IN (SELECT p.demo.id FROM Participant p WHERE p.user.id = :userId AND p.deletedAt IS NULL) " +
         "ORDER BY CASE WHEN i.demoEndDate >= CURRENT_TIMESTAMP THEN 0 ELSE 1 END, i.demoStartDate DESC")
  List<Demo> findParticipatedDemoByUserId(@Param("userId") UUID userId);

  // Search with filters - sorted by participant count (popular)
  @Query(value = "SELECT new product.demo_wave.demo.DemoWithParticipantDTO(" +
          "d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0), " +
          "d.category.id, d.category.jaName, d.prefecture.name, d.user.name) " +
          "FROM Demo d " +
          "LEFT JOIN Participant p ON d.id = p.demo.id AND p.deletedAt IS NULL " +
          "LEFT JOIN Payment pay ON d.id = pay.demo.id " +
          "WHERE d.deletedAt IS NULL " +
          "AND (:categoryId IS NULL OR d.category.id = :categoryId) " +
          "AND (:prefectureId IS NULL OR d.prefecture.id = :prefectureId) " +
          "AND (:keyword IS NULL OR :keyword = '' OR LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
          "GROUP BY d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, d.category.id, d.category.jaName, d.prefecture.name, d.user.name " +
          "ORDER BY COUNT(DISTINCT p.id) DESC, d.demoStartDate DESC")
  List<DemoWithParticipantDTO> searchDemosByPopular(
          @Param("categoryId") Integer categoryId,
          @Param("prefectureId") Integer prefectureId,
          @Param("keyword") String keyword,
          Pageable pageable);

  // Search with filters - sorted by newest
  @Query(value = "SELECT new product.demo_wave.demo.DemoWithParticipantDTO(" +
          "d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0), " +
          "d.category.id, d.category.jaName, d.prefecture.name, d.user.name) " +
          "FROM Demo d " +
          "LEFT JOIN Participant p ON d.id = p.demo.id AND p.deletedAt IS NULL " +
          "LEFT JOIN Payment pay ON d.id = pay.demo.id " +
          "WHERE d.deletedAt IS NULL " +
          "AND (:categoryId IS NULL OR d.category.id = :categoryId) " +
          "AND (:prefectureId IS NULL OR d.prefecture.id = :prefectureId) " +
          "AND (:keyword IS NULL OR :keyword = '' OR LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
          "GROUP BY d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, d.category.id, d.category.jaName, d.prefecture.name, d.user.name " +
          "ORDER BY d.demoStartDate DESC")
  List<DemoWithParticipantDTO> searchDemosByNewest(
          @Param("categoryId") Integer categoryId,
          @Param("prefectureId") Integer prefectureId,
          @Param("keyword") String keyword,
          Pageable pageable);

  // Search with filters - sorted by ending soon
  @Query(value = "SELECT new product.demo_wave.demo.DemoWithParticipantDTO(" +
          "d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0), " +
          "d.category.id, d.category.jaName, d.prefecture.name, d.user.name) " +
          "FROM Demo d " +
          "LEFT JOIN Participant p ON d.id = p.demo.id AND p.deletedAt IS NULL " +
          "LEFT JOIN Payment pay ON d.id = pay.demo.id " +
          "WHERE d.deletedAt IS NULL " +
          "AND (:categoryId IS NULL OR d.category.id = :categoryId) " +
          "AND (:prefectureId IS NULL OR d.prefecture.id = :prefectureId) " +
          "AND (:keyword IS NULL OR :keyword = '' OR LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
          "GROUP BY d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, d.category.id, d.category.jaName, d.prefecture.name, d.user.name " +
          "ORDER BY d.demoEndDate ASC")
  List<DemoWithParticipantDTO> searchDemosByEndingSoon(
          @Param("categoryId") Integer categoryId,
          @Param("prefectureId") Integer prefectureId,
          @Param("keyword") String keyword,
          Pageable pageable);

  // Search with filters - sorted by donation amount
  @Query(value = "SELECT new product.demo_wave.demo.DemoWithParticipantDTO(" +
          "d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0), " +
          "d.category.id, d.category.jaName, d.prefecture.name, d.user.name) " +
          "FROM Demo d " +
          "LEFT JOIN Participant p ON d.id = p.demo.id AND p.deletedAt IS NULL " +
          "LEFT JOIN Payment pay ON d.id = pay.demo.id " +
          "WHERE d.deletedAt IS NULL " +
          "AND (:categoryId IS NULL OR d.category.id = :categoryId) " +
          "AND (:prefectureId IS NULL OR d.prefecture.id = :prefectureId) " +
          "AND (:keyword IS NULL OR :keyword = '' OR LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
          "GROUP BY d.id, d.title, d.content, d.demoPlace, d.demoStartDate, d.demoEndDate, d.category.id, d.category.jaName, d.prefecture.name, d.user.name " +
          "ORDER BY COALESCE(SUM(pay.donateAmount), 0) DESC")
  List<DemoWithParticipantDTO> searchDemosByDonation(
          @Param("categoryId") Integer categoryId,
          @Param("prefectureId") Integer prefectureId,
          @Param("keyword") String keyword,
          Pageable pageable);

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
  List<TodayDemoListRecord> getTodayDemoList(@Param("userId") UUID userId);

  @Query("""
    SELECT new product.demo_wave.api.demoList.FavoriteDemoListRecord(
        d.id, d.title, d.content, c.imageUrl, d.demoStartDate, d.demoEndDate, d.demoPlace,
        d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath,
        COUNT(DISTINCT p.id), COALESCE(SUM(pay.donateAmount), 0)
    )
    FROM Demo d
    LEFT JOIN d.category c
    LEFT JOIN d.user u
    LEFT JOIN Participant p ON p.demo = d
    LEFT JOIN Payment pay ON pay.demo = d
    WHERE d.id IN :favoriteDemoIds
    GROUP BY d.id, d.title, d.content, c.imageUrl, d.demoStartDate, d.demoEndDate, d.demoPlace,
        d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath
""")
  List<FavoriteDemoListRecord> getFavoriteDemoList(@Param("favoriteDemoIds") List<Integer> favoriteDemoIds);

  @Query("""
    SELECT new product.demo_wave.api.demoList.ranking.participation.DemoParticipationRankingRecord(
        d.id,
        d.title,
        d.content,
        c.imageUrl,
        d.demoStartDate,
        d.demoEndDate,
        d.demoPlace,
        d.demoAddressLatitude,
        d.demoAddressLongitude,
        u.name,
        u.profileImagePath,
        COUNT(DISTINCT p.id),
        COALESCE(SUM(pay.donateAmount), 0)
    )
    FROM Demo d
    LEFT JOIN d.category c
    LEFT JOIN d.user u
    LEFT JOIN Participant p ON p.demo = d
    LEFT JOIN Payment pay ON pay.demo = d
    WHERE d.demoStartDate BETWEEN :startOfMonth AND :endOfMonth
    GROUP BY d.id, d.title, d.content, c.imageUrl, d.demoStartDate, d.demoEndDate, d.demoPlace,
             d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath
    ORDER BY COUNT(DISTINCT p.id) DESC
""")
  List<DemoParticipationRankingRecord> getTop10DemoParticipationRankingList(
          @Param("startOfMonth") LocalDateTime startOfMonth,
          @Param("endOfMonth") LocalDateTime endOfMonth
  );

  @Query("""
    SELECT new product.demo_wave.api.demoList.ranking.donation.DemoDonationRankingRecord(
        d.id,
        d.title,
        d.content,
        c.imageUrl,
        d.demoStartDate,
        d.demoEndDate,
        d.demoPlace,
        d.demoAddressLatitude,
        d.demoAddressLongitude,
        u.name,
        u.profileImagePath,
        COUNT(DISTINCT p.id),
        COALESCE(SUM(pay.donateAmount), 0)
    )
    FROM Demo d
    LEFT JOIN d.category c
    LEFT JOIN d.user u
    LEFT JOIN Participant p ON p.demo = d
    LEFT JOIN Payment pay ON pay.demo = d
    WHERE d.demoStartDate BETWEEN :startOfMonth AND :endOfMonth
    GROUP BY d.id, d.title, d.content, c.imageUrl, d.demoStartDate, d.demoEndDate, d.demoPlace,
             d.demoAddressLatitude, d.demoAddressLongitude, u.name, u.profileImagePath
    ORDER BY COALESCE(SUM(pay.donateAmount), 0) DESC
""")
  List<DemoDonationRankingRecord> getTop10DemoDonationRankingList(
          @Param("startOfMonth") LocalDateTime startOfMonth,
          @Param("endOfMonth") LocalDateTime endOfMonth
  );

  @Query("SELECT d.id FROM Demo d WHERE d.demoStartDate BETWEEN :start AND :end")
  List<Integer> findDemoIdsByStartDateBetween(LocalDateTime start, LocalDateTime end);

  // ユーザーが投稿したデモを取得（削除されていないもののみ、開催予定優先、開催日降順）
  @Query("SELECT d FROM Demo d WHERE d.user.id = :userId AND d.deletedAt IS NULL " +
         "ORDER BY CASE WHEN d.demoEndDate >= CURRENT_TIMESTAMP THEN 0 ELSE 1 END, d.demoStartDate DESC")
  List<Demo> findOwnDemosByUserIdOrderByUpcomingFirst(@Param("userId") UUID userId);

  /**
   * ユーザーが参加したデモのうち、支援金を受け取ったデモを取得
   * （GiftTransferが存在する月に開催されたデモ）
   */
  @Query("""
    SELECT DISTINCT d FROM Demo d
    INNER JOIN Participant p ON p.demo = d AND p.user.id = :userId AND p.deletedAt IS NULL
    INNER JOIN GiftTransfer g ON g.user.id = :userId AND g.deletedAt IS NULL
      AND EXTRACT(YEAR FROM d.demoStartDate) = EXTRACT(YEAR FROM g.transferMonth)
      AND EXTRACT(MONTH FROM d.demoStartDate) = EXTRACT(MONTH FROM g.transferMonth)
    WHERE d.deletedAt IS NULL
    ORDER BY d.demoStartDate DESC
  """)
  List<Demo> findReceivedGiftDemosByUserId(@Param("userId") UUID userId);

}
