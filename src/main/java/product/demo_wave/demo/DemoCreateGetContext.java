package product.demo_wave.demo;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DemoCreateGetContext {

  @Getter
  private final ModelAndView modelAndView;

  private final DemoForm demoForm;

  void setModelAndView() {
    this.modelAndView.addObject("demoForm", this.demoForm);
    this.modelAndView.setViewName("demoCreate");
  }

}
