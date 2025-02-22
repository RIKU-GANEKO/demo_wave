package product.demo_wave.api.demoList;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DemoListRecord (
		Integer id, // demo.id
		String title, // demo.title
		String content, // demo.content
		LocalDateTime demoDate, // demo.demoDate
		String demoPlace, // demo.demoPlace
		String demoAddress, // demo.demoAddress
		BigDecimal demoAddressLatitude, // demo.demoAddressLatitude
		BigDecimal demoAddressLongitude, // demo.demoAddressLongitude
		String organizerUserName, // demo.user.name
		LocalDateTime announcementTime, // demo.announcementTime
		Long numberOfDemoParticipants, // 各demoの参加者合計数
		BigDecimal totalDonatedMoney // 各demoの支援金総額
) {

}
