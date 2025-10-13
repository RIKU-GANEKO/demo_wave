package product.demo_wave.common.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {

	UNAUTHORIZED("unauthorized", "APIKeyが間違っています"),
	INTERNAL_SERVER_ERROR("server_error", "API側のエラーです"),
	INVALID_SIGNATURE("invalid_signature", "署名が不正です"),
	DUPLICATE_SUPABASE_UID("duplicate_supabase_uid", "このSupabase UIDはすでに登録されています");

	@Getter
	private final String code;
	@Getter
	private final String description;

}
