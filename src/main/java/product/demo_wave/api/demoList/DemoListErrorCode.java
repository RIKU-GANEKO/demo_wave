package product.demo_wave.api.demoList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
enum DemoListErrorCode {

	UNAUTHORIZED("unauthorized", "APIKeyが間違っています"),
	INTERNAL_SERVER_ERROR("server_error", "API側のエラーです");

	@Getter
	private final String code;
	@Getter
	private final String description;

}
