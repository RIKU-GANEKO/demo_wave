package product.demo_wave.demo;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DemoCreateCompleteGetContext {

  private final DemoForm demoForm;

  @Getter
  private final ModelAndView modelAndView;

  void setModelAndView() {
    this.modelAndView.addObject("demo", demoForm);
    this.modelAndView.setViewName("demoCreateComplete");
  }
}
