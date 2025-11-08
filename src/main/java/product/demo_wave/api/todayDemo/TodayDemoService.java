package product.demo_wave.api.todayDemo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.Participant;
import product.demo_wave.entity.User;
import product.demo_wave.repository.LocationLogsRepository;
import product.demo_wave.repository.ParticipantRepository;
import product.demo_wave.repository.UserRepository;

/**
 * 当日参加予定デモ取得サービス
 */
@Service
@AllArgsConstructor
public class TodayDemoService {

	private final ParticipantRepository participantRepository;
	private final UserRepository userRepository;
	private final LocationLogsRepository locationLogsRepository;

	/**
	 * ログインユーザーの当日参加予定デモを取得
	 *
	 * @param supabaseUid Supabase UID
	 * @return 当日参加予定デモのリスト
	 */
	public List<TodayDemoDTO> getTodayDemos(String supabaseUid) {

		System.out.println("TodayDemoService.getTodayDemos called with UID: " + supabaseUid);

		// ① ユーザー情報を取得
		UUID uuid;
		try {
			uuid = UUID.fromString(supabaseUid);
			System.out.println("Parsed UUID: " + uuid);
		} catch (IllegalArgumentException e) {
			System.err.println("Failed to parse UUID from: " + supabaseUid);
			throw new RuntimeException("Invalid UUID format: " + supabaseUid, e);
		}

		User user = userRepository.findById(uuid)
				.orElseThrow(() -> {
					System.err.println("User not found for UUID: " + uuid);
					return new RuntimeException("User not found");
				});

		System.out.println("User found: " + user.getId());

		// ② ユーザーの全参加予定デモを取得
		List<Participant> participants = participantRepository.findByUserAndDeletedAtIsNull(user);
		System.out.println("Found " + participants.size() + " participant records");

		// ③ 当日開催のデモのみフィルタリング
		LocalDate today = LocalDate.now();
		System.out.println("Today's date: " + today);

		return participants.stream()
				.map(Participant::getDemo)
				.filter(demo -> {
					LocalDate demoDate = demo.getDemoStartDate().toLocalDate();
					boolean isToday = demoDate.isEqual(today);
					System.out.println("Demo ID " + demo.getId() + " date: " + demoDate + " is today: " + isToday);
					return isToday;
				})
				// ④ 開催時間順にソート
				.sorted((d1, d2) -> d1.getDemoStartDate().compareTo(d2.getDemoStartDate()))
				// ⑤ DTOに変換
				.map(demo -> buildTodayDemoDTO(demo, user))
				.collect(Collectors.toList());
	}

	/**
	 * Demo エンティティから TodayDemoDTO を生成
	 */
	private TodayDemoDTO buildTodayDemoDTO(Demo demo, User user) {
		LocalDateTime now = LocalDateTime.now();

		// 開催時間の30分前〜終了時刻まで → チェックイン可能
		LocalDateTime checkInStartTime = demo.getDemoStartDate().minusMinutes(30);
		LocalDateTime checkInEndTime = demo.getDemoEndDate();

		boolean canCheckIn = now.isAfter(checkInStartTime) && now.isBefore(checkInEndTime);

		// 既にチェックイン済みか確認（当日、500m圏内で確認済み）
		boolean hasCheckedIn = locationLogsRepository.existsByUserAndDemoAndTodayAndWithinRadius(
				user.getId(), demo.getId()
		);

		return TodayDemoDTO.builder()
				.demoId(demo.getId())
				.demoTitle(demo.getTitle())
				.demoDescription(demo.getContent())
				.startTime(demo.getDemoStartDate())
				.endTime(demo.getDemoEndDate())
				.venueName(demo.getDemoPlace())
				.latitude(demo.getDemoAddressLatitude())
				.longitude(demo.getDemoAddressLongitude())
				.categoryName(demo.getCategory().getName())
				.canCheckIn(canCheckIn)
				.hasCheckedIn(hasCheckedIn)
				.build();
	}
}
