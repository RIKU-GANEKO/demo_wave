package product.demo_wave.api.favorite;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;

public class FavoriteStatusResponse implements APIResponse {

	@JsonProperty("favorite")
	private FavoriteStatusResponseDTO favoriteStatus;

	public FavoriteStatusResponse(Boolean favoriteStatusEntity) {
		this.favoriteStatus = new FavoriteStatusResponseDTO(
				favoriteStatusEntity.booleanValue()
		);
	}
}

