package product.demo_wave.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * path : http://localhost:8080/system/login?error
 * system向けのログイン画面
 * 
 */
@Controller
@RequestMapping("/login")
class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping
	ModelAndView root(@RequestParam(required = false) Optional<String> error, ModelAndView mv) {
		return loginService.root(error, mv);
	}

}
