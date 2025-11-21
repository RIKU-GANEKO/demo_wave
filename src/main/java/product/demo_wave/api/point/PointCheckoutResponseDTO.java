package product.demo_wave.api.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointCheckoutResponseDTO {
	private String checkoutUrl;
}
