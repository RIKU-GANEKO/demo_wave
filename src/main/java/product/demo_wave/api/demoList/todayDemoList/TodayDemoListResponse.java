package product.demo_wave.api.demoList.todayDemoList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;

public class TodayDemoListResponse implements APIResponse {

	@JsonProperty("demoList")
	private List<TodayDemoListRecord> demoList;

	public TodayDemoListResponse(List<TodayDemoListRecord> demoList) {
		this.demoList = demoList;
	}

}
