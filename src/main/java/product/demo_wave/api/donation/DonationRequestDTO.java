package product.demo_wave.api.donation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class DonationRequestDTO {

	private final long amount;
	private final int demoId;

	@JsonCreator
	public DonationRequestDTO(
			@JsonProperty("amount") int amount,
			@JsonProperty("demoId") int demoId
	) {
		this.amount = amount;
		this.demoId = demoId;
	}
}
