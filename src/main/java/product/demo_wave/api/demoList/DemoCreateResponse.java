package product.demo_wave.api.demoList;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.entity.Demo;

public class DemoCreateResponse implements APIResponse {

	@JsonProperty("demo")
	private DemoResponseDTO demo;

	public DemoCreateResponse(Demo demoEntity) {
		this.demo = new DemoResponseDTO(
				demoEntity.getTitle(),
				demoEntity.getContent(),
				demoEntity.getDemoPlace(),
				demoEntity.getDemoAddressLatitude(),
				demoEntity.getDemoAddressLongitude(),
				demoEntity.getDemoStartDate(),
				demoEntity.getDemoEndDate(),
				demoEntity.getCategory().getId(), // 必要なら null 対応も
				demoEntity.getUser().getId()
		);
	}
}

