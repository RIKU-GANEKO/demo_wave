package product.demo_wave.demo;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class DemoEditConfirmPostContext {

  @Getter
  private final ModelAndView modelAndView;

  private final DemoForm demoForm;

  private final Integer demoId;

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  void updateDemo() {
    demoFacadeDBLogic.updateDemo(demoForm, demoId);
  }

  void setModelAndView() {
    this.modelAndView.addObject("demoId", demoId);
    this.modelAndView.setViewName("redirect:/demo/show?demoId=" + demoId);
  }

}
