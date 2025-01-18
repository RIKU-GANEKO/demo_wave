package product.demo_wave.logic;

import org.springframework.web.bind.annotation.ModelAttribute;

import product.demo_wave.entity.User;
import product.demo_wave.security.UsersDetailsService;

public class GlobalControllerAdvice {

	private UsersDetailsService usersDetailsService;

	@ModelAttribute("loggedInUser")
	public User loggedInUser(){
		return usersDetailsService.getCurrentlyLoggedInUser();
	}
}
