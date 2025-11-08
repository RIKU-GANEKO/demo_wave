package product.demo_wave.api.todayDemo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当日参加予定デモの情報を返すDTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodayDemoDTO {

	private Integer demoId;
	private String demoTitle;
	private String demoDescription;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String venueName;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String categoryName;

	// デモ開催時間の前後かを判定するフラグ
	private Boolean canCheckIn;

	// 既にチェックイン済みか（当日、500m圏内で確認済み）
	private Boolean hasCheckedIn;
}
