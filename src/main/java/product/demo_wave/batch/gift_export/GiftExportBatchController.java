package product.demo_wave.batch.gift_export;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class GiftExportBatchController implements CommandLineRunner {

	private GiftExportBatchService giftExportBatchService;

	@Autowired
	public GiftExportBatchController(GiftExportBatchService giftExportBatchService) {
		this.giftExportBatchService = giftExportBatchService;
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Start Controller");
		GiftExportBatchContext giftExportBatchContext = new GiftExportBatchContext(args);
		giftExportBatchService.export(giftExportBatchContext);
		System.out.println("End Controller");
	}
}
