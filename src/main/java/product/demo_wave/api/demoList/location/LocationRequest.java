package product.demo_wave.api.demoList.location;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class LocationRequest {
	private final Integer demoId;
	private final BigDecimal latitude;
	private final BigDecimal longitude;

	@JsonCreator
	public LocationRequest(@JsonProperty("demoId") Integer demoId, @JsonProperty("latitude") BigDecimal latitude, @JsonProperty("longitude") BigDecimal longitude) {
		this.demoId = demoId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}