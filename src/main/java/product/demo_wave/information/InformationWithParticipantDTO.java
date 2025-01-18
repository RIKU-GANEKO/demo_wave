package product.demo_wave.information;

import java.time.LocalDateTime;

public record InformationWithParticipantDTO(
		Integer id,
		String title,
		String demoPlace,
		LocalDateTime demoDate,
		Long participantCount
) {}
