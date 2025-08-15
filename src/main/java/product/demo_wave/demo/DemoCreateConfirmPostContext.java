package product.demo_wave.demo;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class DemoCreateConfirmPostContext {

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  @Getter
  private final ModelAndView modelAndView;
  private final DemoForm demoForm;
  private final RedirectAttributes redirectAttributes;

  void saveDemo() {
    demoFacadeDBLogic.saveDemo(demoForm);
  }

  void setModelAndView() {
    this.redirectAttributes.addFlashAttribute("demoForm", demoForm);
    this.modelAndView.setViewName("redirect:/demo/create/complete");
  }

}
