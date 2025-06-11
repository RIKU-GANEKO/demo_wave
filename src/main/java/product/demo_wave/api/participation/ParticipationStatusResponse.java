package product.demo_wave.api.participation;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;

public class ParticipationStatusResponse implements APIResponse {

	@JsonProperty("participation")
	private ParticipationStatusResponseDTO participationStatus;

	public ParticipationStatusResponse(Boolean participationStatusEntity) {
		this.participationStatus = new ParticipationStatusResponseDTO(
				participationStatusEntity.booleanValue()
		);
	}
}

