package product.demo_wave.api.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointSendRequestDTO {
	private Integer demoId;  // 送付先デモID
	private Integer points;  // 送付ポイント数
}
