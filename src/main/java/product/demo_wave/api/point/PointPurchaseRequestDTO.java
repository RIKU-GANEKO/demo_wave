package product.demo_wave.api.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointPurchaseRequestDTO {
	private Integer price;   // 支払金額（円）
	private Integer points;  // 購入ポイント数
}
