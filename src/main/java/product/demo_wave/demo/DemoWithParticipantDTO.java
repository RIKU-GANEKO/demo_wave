package product.demo_wave.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DemoWithParticipantDTO(
		Integer id,
		String title,
		String demoPlace,
		LocalDateTime demoStartDate,
		LocalDateTime demoEndDate,
		Long participantCount,
		BigDecimal totalDonationAmount
) {}
