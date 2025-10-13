package product.demo_wave.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
