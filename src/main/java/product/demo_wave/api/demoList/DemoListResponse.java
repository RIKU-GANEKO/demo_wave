package product.demo_wave.api.demoList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;

public class DemoListResponse implements APIResponse {

	@JsonProperty("demoList")
	private List<DemoListRecord> demoList;

	public DemoListResponse(List<DemoListRecord> demoList) {
		this.demoList = demoList;
	}

}
