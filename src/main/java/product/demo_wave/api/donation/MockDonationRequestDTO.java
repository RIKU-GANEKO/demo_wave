package product.demo_wave.api.donation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class MockDonationRequestDTO {

	private final int amount;
	private final int demoId;

	@JsonCreator
	public MockDonationRequestDTO(
			@JsonProperty("amount") int amount,
			@JsonProperty("demoId") int demoId
	) {
		this.amount = amount;
		this.demoId = demoId;
	}
}