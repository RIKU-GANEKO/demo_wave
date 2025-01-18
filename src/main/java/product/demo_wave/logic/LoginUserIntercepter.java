package product.demo_wave.logic;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import product.demo_wave.entity.User;
import product.demo_wave.security.UsersDetailsService;

@Component
public class LoginUserIntercepter implements HandlerInterceptor {
	private UsersDetailsService usersDetailsService;

	@Override
	public void postHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView mv) throws Exception {
		System.out.println("postHandle");
		if (mv != null) {
			ModelMap modelMap = mv.getModelMap();
			User user = usersDetailsService.getCurrentlyLoggedInUser();
			System.out.println(" user details : " + user);
			modelMap.addAttribute("loggedInUser", user);
		}
	}
}
