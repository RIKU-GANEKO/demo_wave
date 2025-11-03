package product.demo_wave.api.favorite;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.entity.FavoriteDemo;

public class FavoriteResponse implements APIResponse {

	@JsonProperty("favorite")
	private FavoriteResponseDTO favorite;

	public FavoriteResponse(FavoriteDemo favoriteEntity) {
		this.favorite = new FavoriteResponseDTO(
				favoriteEntity.getDemo().getId(),
				favoriteEntity.getUser().getId().toString()
		);
	}
}

