package product.demo_wave.common.api;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorCodeResponse implements APIResponse {

	public String error;
	@SerializedName("error_description")
	public String errorDescription;

	public ErrorCodeResponse(String error) {
		this.error = error;
	}

}
