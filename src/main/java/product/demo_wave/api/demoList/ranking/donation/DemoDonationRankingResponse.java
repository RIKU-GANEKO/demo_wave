package product.demo_wave.api.demoList.ranking.donation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;

public class DemoDonationRankingResponse implements APIResponse {

	@JsonProperty("demoList")
	private List<DemoDonationRankingRecord> demoList;

	public DemoDonationRankingResponse(List<DemoDonationRankingRecord> demoList) {
		this.demoList = demoList;
	}

}
