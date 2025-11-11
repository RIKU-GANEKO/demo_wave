package product.demo_wave.mypage;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import product.demo_wave.entity.Demo;

/**
 * 支援したデモと支援金額を扱うDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportedDemoDTO {

    /** デモ情報 */
    private Demo demo;

    /** 支援金額（合計） */
    private BigDecimal totalAmount;
}
