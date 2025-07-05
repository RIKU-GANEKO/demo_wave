package product.demo_wave.api.demoList.ranking.participation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;

public class DemoParticipationRankingResponse implements APIResponse {

	@JsonProperty("demoList")
	private List<DemoParticipationRankingRecord> demoList;

	public DemoParticipationRankingResponse(List<DemoParticipationRankingRecord> demoList) {
		this.demoList = demoList;
	}

}
