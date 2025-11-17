package product.demo_wave.logic;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import product.demo_wave.entity.User;
import product.demo_wave.security.UsersDetailsService;

@ControllerAdvice
@AllArgsConstructor
public class GlobalControllerAdvice {

	private final UsersDetailsService usersDetailsService;

	@ModelAttribute("loggedInUser")
	public User loggedInUser(){
		return usersDetailsService.getCurrentlyLoggedInUser();
	}

	/**
	 * 全てのビューでcontextPathを使用可能にする
	 */
	@ModelAttribute("contextPath")
	public String contextPath(HttpServletRequest request) {
		return request.getContextPath();
	}
}
