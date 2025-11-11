package product.demo_wave.admin.gift;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import product.demo_wave.batch.gift_export.GiftExportBatchDBLogic;
import product.demo_wave.batch.gift_export.GiftExportTarget;
import product.demo_wave.batch.gift_export.UserGiftResult;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.GiftTransfer;
import product.demo_wave.entity.GiftTransferDetail;
import product.demo_wave.entity.User;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.GiftTransferDetailRepository;
import product.demo_wave.repository.GiftTransferRepository;
import product.demo_wave.repository.UserRepository;

/**
 * 管理画面：ギフトカード配布CSV出力サービス
 */
@Service
@AllArgsConstructor
public class AdminGiftExportService {

	private final GiftExportBatchDBLogic giftExportBatchDBLogic;
	private final GiftTransferRepository giftTransferRepository;
	private final GiftTransferDetailRepository giftTransferDetailRepository;
	private final UserRepository userRepository;
	private final DemoRepository demoRepository;

	/**
	 * プレビューデータ取得（CSV出力前の確認用）
	 */
	public GiftExportPreviewDTO getPreview(String targetMonthStr) {
		YearMonth targetMonth = parseYearMonth(targetMonthStr);
		List<GiftExportTarget> demoTargets = giftExportBatchDBLogic.getGiftDistributionData(targetMonth);
		Map<UUID, UserGiftResult> resultMap = aggregateUserGifts(demoTargets);

		// プレビュー用DTOに変換
		List<GiftPreviewEntry> entries = resultMap.values().stream()
				.map(result -> new GiftPreviewEntry(
						result.userId,
						result.email,
						result.amount
				))
				.sorted((a, b) -> b.getAmount().compareTo(a.getAmount()))  // 金額降順
				.toList();

		BigDecimal totalAmount = entries.stream()
				.map(GiftPreviewEntry::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new GiftExportPreviewDTO(
				targetMonth.toString(),
				entries.size(),
				totalAmount,
				entries
		);
	}

	/**
	 * CSV出力 + DB登録（実行）
	 */
	@Transactional
	public GiftExportResultDTO exportCsvAndSaveToDb(String targetMonthStr, UUID adminUserId) {
		YearMonth targetMonth = parseYearMonth(targetMonthStr);
		LocalDate transferMonth = targetMonth.atDay(1);  // 月初日

		try {
			// 1. データ取得・集計
			List<GiftExportTarget> demoTargets = giftExportBatchDBLogic.getGiftDistributionData(targetMonth);
			Map<UUID, UserGiftResult> resultMap = aggregateUserGifts(demoTargets);

			// デモごとの明細を保持（userId -> demoId -> amount）
			Map<UUID, Map<Integer, BigDecimal>> userDemoAmounts = aggregateUserDemoAmounts(demoTargets);

			// 2. CSV生成
			byte[] csvData = generateCsv(resultMap);

			// 3. DB登録（gift_transfers + gift_transfer_details）
			User admin = userRepository.findById(adminUserId)
					.orElseThrow(() -> new RuntimeException("Admin user not found"));

			for (UserGiftResult result : resultMap.values()) {
				User user = userRepository.findById(result.userId)
						.orElseThrow(() -> new RuntimeException("User not found: " + result.userId));

				// gift_transfersに登録（月の合計額）
				GiftTransfer transfer = GiftTransfer.builder()
						.user(user)
						.transferMonth(transferMonth)
						.totalAmount(result.amount)
						.createdBy(admin)
						.build();
				giftTransferRepository.save(transfer);

				// gift_transfer_detailsに登録（デモごとの明細）
				Map<Integer, BigDecimal> demoAmounts = userDemoAmounts.get(result.userId);
				if (demoAmounts != null) {
					for (Map.Entry<Integer, BigDecimal> entry : demoAmounts.entrySet()) {
						Demo demo = demoRepository.findById(entry.getKey())
								.orElseThrow(() -> new RuntimeException("Demo not found: " + entry.getKey()));

						GiftTransferDetail detail = GiftTransferDetail.builder()
								.giftTransfer(transfer)
								.user(user)
								.demo(demo)
								.amount(entry.getValue())
								.build();
						giftTransferDetailRepository.save(detail);
					}
				}
			}

			// 4. 合計金額を計算
			BigDecimal totalAmount = resultMap.values().stream()
					.map(r -> r.amount)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			return new GiftExportResultDTO(
					true,
					csvData,
					resultMap.size(),
					totalAmount,
					null
			);

		} catch (DataIntegrityViolationException e) {
			// 重複エラー（既に登録済み）
			String errorMsg = targetMonth + "は既に登録済みです。";
			throw new IllegalStateException(errorMsg, e);

		} catch (Exception e) {
			// その他のエラー
			String errorMsg = "CSV出力中にエラーが発生しました: " + e.getMessage();
			throw new RuntimeException(errorMsg, e);
		}
	}

	/**
	 * ユーザー別に支援金を合算（既存バッチロジックを流用）
	 */
	private Map<UUID, UserGiftResult> aggregateUserGifts(List<GiftExportTarget> demoTargets) {
		Map<UUID, UserGiftResult> resultMap = new HashMap<>();

		for (GiftExportTarget demo : demoTargets) {
			BigDecimal distributable = demo.getTotalAmount()
					.multiply(BigDecimal.valueOf(90))
					.divide(BigDecimal.valueOf(100), RoundingMode.DOWN);

			List<UUID> participantIds = demo.getParticipantIds() != null
					? demo.getParticipantIds()
					: Collections.emptyList();
			Map<UUID, String> userEmails = demo.getUserEmails() != null
					? demo.getUserEmails()
					: Collections.emptyMap();

			BigDecimal perUser = participantIds.isEmpty()
					? BigDecimal.ZERO
					: distributable.divide(BigDecimal.valueOf(participantIds.size()), RoundingMode.DOWN);

			for (UUID userId : participantIds) {
				String email = userEmails.getOrDefault(userId, "");
				UserGiftResult result = resultMap.computeIfAbsent(
						userId,
						id -> new UserGiftResult(id, email, BigDecimal.ZERO)
				);
				result.amount = result.amount.add(perUser);
			}
		}
		return resultMap;
	}

	/**
	 * CSV生成（メモリ上）
	 */
	private byte[] generateCsv(Map<UUID, UserGiftResult> resultMap) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(baos, StandardCharsets.UTF_8))) {
			// ヘッダー
			writer.println("user_id,email,gift_amount");

			// データ行
			for (UserGiftResult result : resultMap.values()) {
				writer.printf("%s,%s,%s%n",
						result.userId,
						result.email,
						result.amount.toPlainString()
				);
			}
		}
		return baos.toByteArray();
	}

	/**
	 * ユーザーごと・デモごとの支援金を集計
	 * @return Map<userId, Map<demoId, amount>>
	 */
	private Map<UUID, Map<Integer, BigDecimal>> aggregateUserDemoAmounts(List<GiftExportTarget> demoTargets) {
		Map<UUID, Map<Integer, BigDecimal>> result = new HashMap<>();

		for (GiftExportTarget demo : demoTargets) {
			BigDecimal distributable = demo.getTotalAmount()
					.multiply(BigDecimal.valueOf(90))
					.divide(BigDecimal.valueOf(100), RoundingMode.DOWN);

			List<UUID> participantIds = demo.getParticipantIds() != null
					? demo.getParticipantIds()
					: Collections.emptyList();

			if (participantIds.isEmpty()) {
				continue;
			}

			BigDecimal perUser = distributable.divide(
					BigDecimal.valueOf(participantIds.size()),
					RoundingMode.DOWN
			);

			for (UUID userId : participantIds) {
				result.computeIfAbsent(userId, k -> new HashMap<>())
					.put(demo.getDemoId(), perUser);
			}
		}

		return result;
	}

	/**
	 * 文字列 → YearMonth変換
	 */
	private YearMonth parseYearMonth(String str) {
		// "2025-01" 形式を想定
		String[] parts = str.split("-");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		return YearMonth.of(year, month);
	}
}
