package product.demo_wave.api.donation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import product.demo_wave.common.api.APIResponse;

/**
 * ユーザ情報取得用 Service
 */
@Service
@AllArgsConstructor
public class DonationService {

	public ResponseEntity<DonationCheckoutResponseDTO> createCheckoutSession(DonationContext context) {
		return context.createCheckoutSession();
	}
}
