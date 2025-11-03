package product.demo_wave.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Demo;
import product.demo_wave.entity.Participant;
import product.demo_wave.entity.User;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

//  List<Participant> findByDemoId(Integer demoId);

//  List<Participant> findByUserId(Integer userId);

  // `demo_id` に基づいて、現在のユーザーが参加しているかどうかをチェック
  boolean existsByDemoAndUserAndDeletedAtIsNull(Demo demo, User user);

  Optional<Participant> findByDemoAndUser(Demo demo, User user);

  // `demoId` を元に participant の合計数を取得
  Integer countByDemo(Demo demo);

  // デモの参加者リストを取得（削除されていないもののみ）
  List<Participant> findByDemoAndDeletedAtIsNull(Demo demo);

  // 投稿者以外の参加者数を取得（編集可否判定用）
  @Query("SELECT COUNT(p) FROM Participant p " +
         "WHERE p.demo = :demo " +
         "AND p.user.id != :userId " +
         "AND p.deletedAt IS NULL")
  Long countOtherParticipants(@Param("demo") Demo demo, @Param("userId") UUID userId);

}
