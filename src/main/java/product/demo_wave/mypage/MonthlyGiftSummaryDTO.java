package product.demo_wave.mypage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 月ごとの受取支援金サマリーDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyGiftSummaryDTO {

    /** 対象月（YYYY-MM-DD形式、月初日） */
    private LocalDate transferMonth;

    /** 月の合計受取金額 */
    private BigDecimal totalAmount;

    /** デモごとの詳細リスト */
    private List<DemoGiftDetailDTO> details;

    /**
     * 表示用の月文字列を取得（例: "2025年6月"）
     */
    public String getMonthDisplay() {
        return transferMonth.getYear() + "年" + transferMonth.getMonthValue() + "月";
    }
}
