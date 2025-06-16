package product.demo_wave.api.donation.webhook;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.annotation.CustomRetry;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.Payment;
import product.demo_wave.entity.User;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.PaymentRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class WebhookDBLogic {

//	private static final Logger logger = Logger.getLogger(UserListDBLogic.class.getSimpleName());

	private final PaymentRepository paymentRepository;
	private final DemoRepository demoRepository;
	private final UserRepository userRepository;

	/**
	 *
	 */
	@CustomRetry
	Payment saveDonation(String firebaseUid, BigDecimal amount, Integer demoId) {
		Payment newPayment = paymentRepository.saveAndFlush(toEntity(firebaseUid, amount, demoId));
		return newPayment;
	}

	private Payment toEntity(String firebaseUid, BigDecimal amount, Integer demoId) {

		Payment payment = new Payment();

		payment.setDemo(fetchDemo(demoId));
		payment.setUser(fetchUser(firebaseUid));
		payment.setDonateAmount(amount);

		return payment;
	}

	@CustomRetry
	Demo fetchDemo(Integer demoId) {
		Optional<Demo> demo = demoRepository.findById(demoId);
		return demo.orElse(new Demo());
	}

	User fetchUser(String firebaseUid) {
		Optional<User> user = userRepository.findByFirebaseUid(firebaseUid);
		return user.orElse(new User());
	}

}
