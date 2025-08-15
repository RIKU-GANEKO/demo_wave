package product.demo_wave.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
    @NotBlank(message = "デモ住所を入力してください")
    String demoAddress,

    BigDecimal demoAddressLatitude,

    BigDecimal demoAddressLongitude,

    @NotNull(message = "カテゴリを選択してください")
    Integer categoryId,

    @NotNull(message = "都道府県を選択してください")
    Integer prefectureId
) {}