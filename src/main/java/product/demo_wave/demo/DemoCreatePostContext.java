package product.demo_wave.demo;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DemoCreatePostContext {

  @Getter
  private final ModelAndView modelAndView;
  private final DemoForm demoForm;
  private final BindingResult result;
  private final RedirectAttributes redirectAttributes;

  boolean hasErrors() {
    return this.result.hasErrors();
  }

  void setErrorModelAndView() {
    this.modelAndView.setViewName("demoCreate");
  }

  void setModelAndView() {
    this.redirectAttributes.addFlashAttribute("demoForm", demoForm);
    this.modelAndView.setViewName("redirect:/demo/create/confirm");
  }
}
