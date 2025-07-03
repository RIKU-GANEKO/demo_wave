package product.demo_wave.api.demoList;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FavoriteDemoListRecord(
		Integer id, // demo.id
		String title, // demo.title
		String content, // demo.content
		String categoryImageUrl,
		LocalDateTime demoStartDate, // demo.demoStartDate
		LocalDateTime demoEndDate, // demo.demoEndDate
		String demoPlace, // demo.demoPlace
		BigDecimal demoAddressLatitude, // demo.demoAddressLatitude
		BigDecimal demoAddressLongitude, // demo.demoAddressLongitude
		String organizerUserName, // demo.user.name
		String hostProfileImagePath, // 各デモの主催者のプロフィール画像
		Long numberOfDemoParticipants, // 各demoの参加者合計数
		BigDecimal totalDonatedMoney // 各demoの支援金総額
) {

}
