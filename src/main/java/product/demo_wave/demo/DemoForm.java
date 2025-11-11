package product.demo_wave.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import product.demo_wave.entity.Demo;

record DemoForm(
    Integer id,

    @Size(max = 255, message = "タイトルは255文字以内にしてください")
    @NotBlank(message = "タイトルを入力してください")
    String title,

    @NotBlank(message = "内容を入力してください")
    String content,

    @NotNull(message = "開催日を設定してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate demoDate,

    @NotNull(message = "開始時間を設定してください")
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime demoStartTime,

    @NotNull(message = "終了時間を設定してください")
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime demoEndTime,

    @Size(max = 255, message = "デモ会場は255文字以内にしてください")
    @NotBlank(message = "デモ会場を入力してください")
    String demoPlace,

    @Size(max = 255, message = "デモ住所は255文字以内にしてください")
    String demoAddress,

    BigDecimal demoAddressLatitude,

    BigDecimal demoAddressLongitude,

    @NotNull(message = "カテゴリを選択してください")
    Integer categoryId,

    Integer prefectureId,

    @Size(max = 500, message = "活動報告URLは500文字以内にしてください")
    String activityReportUrl
) {
    /**
     * DemoエンティティからDemoFormを生成
     */
    public static DemoForm fromEntity(Demo demo) {
        return new DemoForm(
            demo.getId(),
            demo.getTitle(),
            demo.getContent(),
            demo.getDemoStartDate().toLocalDate(),
            demo.getDemoStartDate().toLocalTime(),
            demo.getDemoEndDate().toLocalTime(),
            demo.getDemoPlace(),
            null, // demoAddress は保存していないため null
            demo.getDemoAddressLatitude(),
            demo.getDemoAddressLongitude(),
            demo.getCategory() != null ? demo.getCategory().getId() : null,
            demo.getPrefecture() != null ? demo.getPrefecture().getId() : null,
            demo.getActivityReportUrl()
        );
    }
}