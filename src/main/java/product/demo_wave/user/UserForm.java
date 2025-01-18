package product.demo_wave.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

record UserForm(
//		Integer id,
//		Integer accountId,
//		String accountName,

		@NotBlank(message = "名前を入力してください")
		String name,

		@Email(message = "メールアドレスの形式が正しくありません")
		@NotBlank(message = "メールアドレスを入力してください")
		String email,

		@Size(min = 6, max = 20, message = "パスワードは6文字以上20文字以内にしてください")
		@Pattern(regexp = ".*[a-z].*", message = "パスワードには小文字を含めるようにしてください")
		@Pattern(regexp = ".*[A-Z].*", message = "パスワードには大文字を含めるようにしてください")
		@Pattern(regexp = ".*\\d.*", message = "パスワードには半角数字を含めるようにしてください")
		@NotBlank(message = "パスワードを入力してください")
		String password
) {
}
