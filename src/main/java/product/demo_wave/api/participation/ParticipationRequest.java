package product.demo_wave.api.participation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ParticipationRequest {
	private final Integer demoId;

	@JsonCreator
	public ParticipationRequest(@JsonProperty("demoId") Integer demoId) {
		this.demoId = demoId;
	}
}

