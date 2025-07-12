package product.demo_wave.batch.gift_export;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class GiftExportBatchContext {

	@Setter
	private GiftExportBatchDBLogic giftExportBatchDBLogic;

	@Getter
	private YearMonth targetMonth;

	private static final DateTimeFormatter CSV_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

	// 対象月をargsから解析
	public GiftExportBatchContext(String[] args) {
		this.targetMonth = resolveTargetMonth(args);
		System.out.println("targetMonth: " + targetMonth);
	}

	/**
	 * メイン処理（CSV出力）
	 */
	public void giftCsvExport() throws Exception {
		List<GiftExportTarget> demoTargets = giftExportBatchDBLogic.getGiftDistributionData(targetMonth);
		Map<Integer, UserGiftResult> resultMap = aggregateUserGifts(demoTargets);
		writeCsv(resultMap, targetMonth);
	}

	/**
	 * ユーザー別に支援金を合算する
	 */
	private Map<Integer, UserGiftResult> aggregateUserGifts(List<GiftExportTarget> demoTargets) {
		Map<Integer, UserGiftResult> resultMap = new HashMap<>();

		for (GiftExportTarget demo : demoTargets) {
			BigDecimal distributable = demo.getTotalAmount()
					.multiply(BigDecimal.valueOf(90))
					.divide(BigDecimal.valueOf(100), RoundingMode.DOWN); // ← 小数点切り捨て
			List<Integer> participantIds = demo.getParticipantIds() != null ? demo.getParticipantIds() : Collections.emptyList();
			Map<Integer, String> userEmails = demo.getUserEmails() != null ? demo.getUserEmails() : Collections.emptyMap();

			BigDecimal perUser = participantIds.isEmpty()
					? BigDecimal.ZERO
					: distributable.divide(BigDecimal.valueOf(participantIds.size()), RoundingMode.DOWN);

			for (Integer userId : participantIds) {
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
	 * 結果をCSVファイルとして出力
	 */
	private void writeCsv(Map<Integer, UserGiftResult> resultMap, YearMonth month) throws Exception {
		String fileName = "/tmp/demo_wave/" + month.format(CSV_MONTH_FORMATTER) + "-gift.csv";

		try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(fileName)))) {
			writer.println("user_id,email,gift_amount");
			for (UserGiftResult res : resultMap.values()) {
				writer.printf("%d,%s,%s%n", res.userId, res.email, res.amount.toPlainString());
			}
		}
		System.out.println("✅ CSV出力完了: " + fileName);
	}

	/**
	 * 引数から対象月を解析する
	 */
	private YearMonth resolveTargetMonth(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if ("-year_month".equals(args[i]) && i + 1 < args.length) {
				String ym = args[i + 1];
				if (ym.matches("^[0-9]{6}$")) {
					try {
						int year = Integer.parseInt(ym.substring(0, 4));
						int month = Integer.parseInt(ym.substring(4, 6));
						return YearMonth.of(year, month).minusMonths(1);
					} catch (Exception e) {
						System.err.println("⚠️ 年月引数が不正です: " + ym);
					}
				} else {
					System.err.println("⚠️ 年月引数は YYYYMM 形式で指定してください: " + ym);
				}
			}
		}
		return YearMonth.now().minusMonths(1);
	}
}
