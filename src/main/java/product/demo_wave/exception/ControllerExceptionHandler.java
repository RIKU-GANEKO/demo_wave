package product.demo_wave.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 各種 Controller クラスで例外が発生した時、共通の例外処理を行うクラス
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/error");
		return mv;
	}
}
