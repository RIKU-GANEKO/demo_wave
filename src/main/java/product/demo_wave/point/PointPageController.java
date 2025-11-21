package product.demo_wave.point;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.demo_wave.entity.PointBalance;
import product.demo_wave.entity.User;
import product.demo_wave.logic.GetUserLogic;
import product.demo_wave.service.PointService;

/**
 * ポイント購入画面用コントローラー
 */
@Slf4j
@Controller
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointPageController {

	private final PointService pointService;
	private final GetUserLogic getUserLogic;

	/**
	 * ポイント購入画面を表示
	 */
	@GetMapping("/purchase")
	public ModelAndView purchasePage(ModelAndView mv) {
		try {
			User user = getUserLogic.getUserFromCache();
			PointBalance balance = pointService.getOrCreatePointBalance(user.getId());

			mv.addObject("userPoints", balance.getBalance());
			mv.addObject("loggedInUser", user);
			mv.setViewName("point/purchase");

			log.info("ポイント購入画面表示: user={}, balance={}", user.getId(), balance.getBalance());
			return mv;

		} catch (UsernameNotFoundException e) {
			// 未ログインの場合はログインページへリダイレクト
			mv.setViewName("redirect:/login");
			return mv;
		}
	}
}
