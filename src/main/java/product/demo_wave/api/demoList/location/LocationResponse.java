package product.demo_wave.api.demoList.location;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.entity.LocationLogs;

public class LocationResponse implements APIResponse {

	@JsonProperty("location")
	private LocationResponseDTO locationResponseDTO;

	public LocationResponse(LocationLogs locationLogsEntity) {
		this.locationResponseDTO = new LocationResponseDTO(
				locationLogsEntity.getIsWithinRadius()
		);
	}
}

