package product.demo_wave.information;

import jakarta.validation.constraints.NotBlank;

record CommentForm(
    Integer id,

    Integer informationId,

    @NotBlank(message = "内容を入力してください")
    String content
) {}