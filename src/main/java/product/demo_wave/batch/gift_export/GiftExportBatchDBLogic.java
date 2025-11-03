package product.demo_wave.batch.gift_export;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.LocationLogsRepository;
import product.demo_wave.repository.PaymentRepository;
import product.demo_wave.repository.UserRepository;

@Component
class GiftExportBatchDBLogic {

	private final DemoRepository demoRepository;
	private final PaymentRepository paymentRepository;
	private final LocationLogsRepository locationLogsRepository;
	private final UserRepository userRepository;

	@Autowired
	public GiftExportBatchDBLogic(
			DemoRepository demoRepository,
			PaymentRepository paymentRepository,
			LocationLogsRepository locationLogsRepository,
			UserRepository userRepository
			) {
		this.demoRepository = demoRepository;
		this.paymentRepository = paymentRepository;
		this.locationLogsRepository = locationLogsRepository;
		this.userRepository = userRepository;
	}

	// ここで実際はRepositoryを使ってDBアクセスする
	public List<GiftExportTarget> getGiftDistributionData(YearMonth targetMonth) {

//		return new ArrayList<>();

		LocalDateTime start = targetMonth.atDay(1).atStartOfDay();
		LocalDateTime end = targetMonth.atEndOfMonth().atTime(23, 59, 59);

		// ① 対象月に開催されたデモ
		List<Integer> demoIds = demoRepository.findDemoIdsByStartDateBetween(start, end);
		System.out.println(targetMonth + "月に開催されたデモのdemoId: " + demoIds);

		// ② 各デモの支援金合計
		Map<Integer, BigDecimal> paymentSums = paymentRepository.findTotalAmountsByDemoIds(demoIds)
				.stream().collect(Collectors.toMap(PaymentSum::getDemoId, PaymentSum::getTotalAmount));
		System.out.println("paymentSum: " + paymentSums);

		// ③ 各デモの参加者（is_within_radius = 1）
		Map<Integer, List<java.util.UUID>> demoParticipants = locationLogsRepository.findParticipantsByDemoIds(demoIds)
				.stream().collect(Collectors.groupingBy(
						ParticipantEntry::getDemoId,
						Collectors.mapping(ParticipantEntry::getUserId, Collectors.toList())
				));
		System.out.println("demoParticipants: " + demoParticipants);

		// ④ 参加者のメールアドレス（重複排除）
		Set<java.util.UUID> allUserIds = demoParticipants.values().stream()
				.flatMap(List::stream).collect(Collectors.toSet());
		Map<java.util.UUID, String> userEmails = userRepository.findEmailsByUserIds(allUserIds)
				.stream().collect(Collectors.toMap(UserEmail::getUserId, UserEmail::getEmail));
		System.out.println("userEmails: " + userEmails);

		// ⑤ 組み立て
		List<GiftExportTarget> result = new ArrayList<>();
		for (Integer demoId : demoIds) {
			BigDecimal totalAmount = paymentSums.getOrDefault(demoId, BigDecimal.ZERO);
			List<java.util.UUID> participants = demoParticipants.getOrDefault(demoId, Collections.emptyList());
			Map<java.util.UUID, String> emails = participants.stream()
					.collect(Collectors.toMap(id -> id, id -> userEmails.getOrDefault(id, "")));

			result.add(new GiftExportTarget(demoId, totalAmount, participants, emails));
		}
		return result;

	}
}