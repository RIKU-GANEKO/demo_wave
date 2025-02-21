package product.demo_wave.demo;

import jakarta.validation.constraints.NotBlank;

record CommentForm(
    Integer id,

    Integer demoId,

    @NotBlank(message = "内容を入力してください")
    String content
) {}