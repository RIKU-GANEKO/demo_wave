package product.demo_wave.payment;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.common.logic.BasicFacadeDBLogic;
import product.demo_wave.entity.Payment;
import product.demo_wave.repository.PaymentRepository;

@Component
@AllArgsConstructor
class PaymentFacadeDBLogic extends BasicFacadeDBLogic {

    private final PaymentRepository paymentRepository;

    @CustomRetry
    void savePayment(PaymentDTO paymentDTO) {

        Payment payment = new Payment();

        payment.setInformationId(paymentDTO.informationId());
        payment.setDonateUserId(paymentDTO.donateUserId());
        payment.setDonateAmount(paymentDTO.donatedAmount());

        paymentRepository.saveAndFlush(payment);
    }

}
