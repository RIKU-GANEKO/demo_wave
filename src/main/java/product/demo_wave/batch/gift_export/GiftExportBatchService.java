package product.demo_wave.batch.gift_export;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class GiftExportBatchService {

	private final GiftExportBatchDBLogic giftExportBatchDBLogic;

	@Autowired
	GiftExportBatchService(GiftExportBatchDBLogic giftExportBatchDBLogic) {
		this.giftExportBatchDBLogic = giftExportBatchDBLogic;
	}

	public void export(GiftExportBatchContext giftExportBatchContext) throws Exception {

		giftExportBatchContext.setGiftExportBatchDBLogic(giftExportBatchDBLogic);

		giftExportBatchContext.giftCsvExport();

	}

//	System.out.println("Start Service");
//	YearMonth targetMonth = YearMonth.now().minusMonths(1);
//	List<GiftExportBatchContext> demoContexts = dbLogic.getGiftDistributionData(targetMonth);
//
//	// ユーザー単位で合算
//	Map<Long, UserGiftResult> resultMap = new HashMap<>();
//		for (GiftExportBatchContext demo : demoContexts) {
//		long distributable = demo.totalAmount * 90 / 100; // ① 90%
//		long perUser = demo.participantIds.isEmpty() ? 0 : distributable / demo.participantIds.size(); // ②/③ 切り捨て
//
//		for (Long userId : demo.participantIds) {
//			UserGiftResult res = resultMap.computeIfAbsent(userId, id -> new UserGiftResult(id, demo.userEmails.get(id), 0L));
//			res.amount += perUser;
//		}
//	}
//
//	// CSV出力
//	String fileName = "/tmp/demo_wave/" + targetMonth.toString().replace("-", "") + "-gift.csv";
//		try (PrintWriter writer = new PrintWriter(new FileWriter(Paths.get(fileName).toFile()))) {
//		writer.println("user_id,email,gift_amount");
//		for (UserGiftResult res : resultMap.values()) {
//			writer.printf("%d,%s,%d\n", res.userId, res.email, res.amount);
//		}
//	}
//		System.out.println("End Service");
}