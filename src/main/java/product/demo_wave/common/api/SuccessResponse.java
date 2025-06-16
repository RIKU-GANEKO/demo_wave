package product.demo_wave.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SuccessResponse implements APIResponse {
	private final boolean success;
	private final String message;

	public static SuccessResponse of(String message) {
		return SuccessResponse.builder()
				.success(true)
				.message(message)
				.build();
	}
}
