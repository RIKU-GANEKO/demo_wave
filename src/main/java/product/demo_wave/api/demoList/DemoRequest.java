package product.demo_wave.api.demoList;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DemoRequest {
	private final String title;
	private final String content;
	private final String demoPlace;
	private final Integer prefectureId;
	private final BigDecimal demoAddressLatitude;
	private final BigDecimal demoAddressLongitude;
	private final LocalDateTime demoStartDate;  // ISO8601の日時文字列を受け取るならStringでOK。Date型に変換することも可能。
	private final LocalDateTime demoEndDate;  // ISO8601の日時文字列を受け取るならStringでOK。Date型に変換することも可能。
	private final Integer categoryId;
	private final Integer userId;
}

