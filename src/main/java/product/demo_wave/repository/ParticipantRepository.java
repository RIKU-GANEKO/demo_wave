package product.demo_wave.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

  List<Participant> findByInformationId(Integer informationId);

  List<Participant> findByUserId(Integer userId);

  // `information_id` に基づいて、現在のユーザーが参加しているかどうかをチェック
  boolean existsByInformationIdAndUserIdAndDeletedAtIsNull(Integer informationId, Integer userId);

  Optional<Participant> findByInformationIdAndUserId(Integer informationId, Integer userId);


}
