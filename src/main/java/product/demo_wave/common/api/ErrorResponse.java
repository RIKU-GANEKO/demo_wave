package product.demo_wave.common.api;

public class ErrorResponse implements APIResponse {

	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	// 必要に応じてsetterやtoStringなども追加

}
