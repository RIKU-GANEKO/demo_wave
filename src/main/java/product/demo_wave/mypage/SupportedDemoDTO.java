package product.demo_wave.mypage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import product.demo_wave.entity.Demo;

/**
 * 支援したデモと支援ポイント数を扱うDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportedDemoDTO {

    /** デモ情報 */
    private Demo demo;

    /** 支援ポイント数（合計） */
    private Integer totalPoints;
}
