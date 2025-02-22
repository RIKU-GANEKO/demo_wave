package product.demo_wave.api.commentList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
enum CommentListErrorCode {

	UNAUTHORIZED("unauthorized", "APIKeyが間違っています"),
	INTERNAL_SERVER_ERROR("server_error", "API側のエラーです");

	@Getter
	private final String code;
	@Getter
	private final String description;

}
