package product.demo_wave.user;

import org.springframework.web.multipart.MultipartFile;

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

		@Size(min = 8, message = "パスワードは8文字以上にしてください")
		@NotBlank(message = "パスワードを入力してください")
		String password,

		// プロフィール画像ファイル（オプション）
		MultipartFile profileImage
) {
}
