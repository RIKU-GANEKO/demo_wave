package product.demo_wave.api.commentList;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import product.demo_wave.api.commentList.APIResponse;

@AllArgsConstructor
public class ErrorCodeResponse implements APIResponse {

	public String error;
	@SerializedName("error_description")
	public String errorDescription;

	public ErrorCodeResponse(String error) {
		this.error = error;
	}

}
