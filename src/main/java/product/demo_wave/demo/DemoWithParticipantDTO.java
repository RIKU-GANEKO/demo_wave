package product.demo_wave.demo;

import java.time.LocalDateTime;

public record DemoWithParticipantDTO(
		Integer id,
		String title,
		String demoPlace,
		LocalDateTime demoStartDate,
		Long participantCount
) {}
