package product.demo_wave.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @NotNull(message = "公開日時を設定してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime announcementTime,

    @NotNull(message = "デモ日時を設定してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime demoDate,

    @Size(max = 255, message = "デモ会場は255文字以内にしてください")
    @NotBlank(message = "デモ会場を入力してください")
    String demoPlace,

    @Size(max = 255, message = "デモ住所は255文字以内にしてください")
    @NotBlank(message = "デモ住所を入力してください")
    String demoAddress,

    BigDecimal demoAddressLatitude,

    BigDecimal demoAddressLongitude
) {}