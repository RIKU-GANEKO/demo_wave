package product.demo_wave.api.demoList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.entity.Information;

public class DemoListResponse implements APIResponse {

	@JsonProperty("demoList")
//	private List<DemoListRecord> demoList;
	private List<Information> demoList;

//	public DemoListResponse(List<DemoListRecord> demoList) {
	public DemoListResponse(List<Information> demoList) {
		this.demoList = demoList;
	}

}
