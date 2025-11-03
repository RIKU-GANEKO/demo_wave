package product.demo_wave.batch.gift_export;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEmail {
	private UUID userId;
	private String email;
}
