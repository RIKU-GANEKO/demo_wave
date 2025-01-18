package product.demo_wave.login;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
class LoginService {

	ModelAndView root(Optional<String> error, ModelAndView mv) {
		try {
			mv.setViewName("login");
			mv.addObject("title", "ログイン");
			if (error.isPresent()) {
				mv.addObject("errorMessage", "ユーザー名またはパスワードが適切ではありません");
			}
		} catch (Exception e) {
			throw e;
		}

		return mv;
	}
}
