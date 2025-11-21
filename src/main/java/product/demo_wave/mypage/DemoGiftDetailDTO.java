package product.demo_wave.mypage;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * デモごとの受取報酬額詳細DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoGiftDetailDTO {

    /** デモID */
    private Integer demoId;

    /** デモ名 */
    private String demoName;

    /** 受取金額 */
    private BigDecimal amount;

    /** デモ開催日（表示用） */
    private String demoDate;
}
