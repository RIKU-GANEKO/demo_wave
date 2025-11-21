package product.demo_wave.demo;

import java.time.LocalDateTime;

public record DemoWithParticipantDTO(
		Integer id,
		String title,
		String content,
		String demoPlace,
		LocalDateTime demoStartDate,
		LocalDateTime demoEndDate,
		Long participantCount,
		Long totalPoints,  // 応援ポイント総数（SUM関数がLongを返すため）
		Integer categoryId,
		String categoryName,  // 日本語カテゴリー名（ja_nameから取得）
		String prefectureName,
		String organizerName
) {}
