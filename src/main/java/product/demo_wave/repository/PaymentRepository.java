package product.demo_wave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
