package product.demo_wave.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.common.logic.BasicFacadeDBLogic;
import product.demo_wave.entity.Account;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.Payment;
import product.demo_wave.entity.User;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.PaymentRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
class PaymentFacadeDBLogic extends BasicFacadeDBLogic {

    private final PaymentRepository paymentRepository;
    private final DemoRepository demoRepository;
    private final UserRepository userRepository;

    @CustomRetry
    public Demo getDemo(Integer demoId) {
        Optional<Demo> demo = demoRepository.findById(demoId);
        return demo.orElse(new Demo());
    }

    @CustomRetry
    public User getDonateUser(Integer donateUserId) {
        Optional<User> user = userRepository.findById(donateUserId);
        return user.orElse(new User());
    }

    @CustomRetry
    void savePayment(PaymentDTO paymentDTO) {

        Payment payment = new Payment();

        payment.setDemo(paymentDTO.demo());
        payment.setUser(paymentDTO.donateUser());
        payment.setDonateAmount(paymentDTO.donatedAmount());

        paymentRepository.saveAndFlush(payment);
    }

}
