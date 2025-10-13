package product.demo_wave.api.participation;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.annotation.CustomRetry;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.Participant;
import product.demo_wave.entity.User;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.ParticipantRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class ParticipationDBLogic {

//	private static final Logger logger = Logger.getLogger(ParticipationListDBLogic.class.getSimpleName());

	private final ParticipantRepository participantRepository;
	private final DemoRepository demoRepository;
	private final UserRepository userRepository;

	/**
	 *
	 */
	@CustomRetry
	public Participant saveParticipation(String supabaseUid, ParticipationRequest request) {
		User user = fetchUser(supabaseUid);
		Demo demo = fetchDemo(request.getDemoId());

		Optional<Participant> existingParticipantOpt = participantRepository.findByDemoAndUser(demo, user);

		if (existingParticipantOpt.isPresent()) {
			Participant existing = existingParticipantOpt.get();

			if (existing.getDeletedAt() == null) {
				// 登録済み → 不参加として削除
				existing.setDeletedAt(LocalDateTime.now());
				return participantRepository.saveAndFlush(existing);
			}
		}

		// 未登録 → 新規作成
		Participant newParticipant = new Participant();
		newParticipant.setUser(user);
		newParticipant.setDemo(demo);

		return participantRepository.saveAndFlush(newParticipant);
	}


	User fetchUser(String supabaseUid) {
		Optional<User> user = userRepository.findBySupabaseUid(supabaseUid);
		return user.orElse(new User());
	}

	Demo fetchDemo(Integer demoId) {
		Optional<Demo> demo = demoRepository.findById(demoId);
		return demo.orElse(new Demo());
	}

	/**
	 *
	 */
	@CustomRetry
	public Boolean getParticipationStatus(String supabaseUid, Integer demoId) {
		Boolean isParticipant = participantRepository.existsByDemoAndUserAndDeletedAtIsNull(fetchDemo(demoId), fetchUser(supabaseUid));
		return isParticipant;
	}
}
