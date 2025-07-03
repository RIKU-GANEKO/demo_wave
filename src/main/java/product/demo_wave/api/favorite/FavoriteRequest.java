package product.demo_wave.api.favorite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class FavoriteRequest {
	private final Integer demoId;

	@JsonCreator
	public FavoriteRequest(@JsonProperty("demoId") Integer demoId) {
		this.demoId = demoId;
	}
}

