package product.demo_wave.common.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {

	UNAUTHORIZED("unauthorized", "APIKeyが間違っています"),
	INTERNAL_SERVER_ERROR("server_error", "API側のエラーです");

	@Getter
	private final String code;
	@Getter
	private final String description;

}
