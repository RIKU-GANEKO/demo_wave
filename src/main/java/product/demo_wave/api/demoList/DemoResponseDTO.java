package product.demo_wave.api.demoList;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DemoResponseDTO {
	private String title;
	private String content;
	private String demoPlace;
	private Integer prefectureId;
	private BigDecimal demoAddressLatitude;
	private BigDecimal demoAddressLongitude;
	private LocalDateTime demoStartDate;
	private LocalDateTime demoEndDate;
	private Integer categoryId;
	private Integer userId;
}
