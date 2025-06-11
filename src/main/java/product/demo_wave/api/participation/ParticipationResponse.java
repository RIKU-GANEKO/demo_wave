package product.demo_wave.api.participation;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.entity.Participant;

public class ParticipationResponse implements APIResponse {

	@JsonProperty("participation")
	private ParticipationResponseDTO participation;

	public ParticipationResponse(Participant participationEntity) {
		this.participation = new ParticipationResponseDTO(
				participationEntity.getDemo().getId(),
				participationEntity.getUser().getId()
		);
	}
}

