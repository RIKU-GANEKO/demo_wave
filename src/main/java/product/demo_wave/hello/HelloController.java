package product.demo_wave.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping("/hello")
	public String helloWorld(Model model) {
		model.addAttribute("message", "Hello World!");
		return "hello";  // hello.html を表示
	}

}
