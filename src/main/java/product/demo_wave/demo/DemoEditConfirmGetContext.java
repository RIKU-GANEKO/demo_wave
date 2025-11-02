package product.demo_wave.demo;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import product.demo_wave.entity.Category;
import product.demo_wave.entity.Prefecture;

@RequiredArgsConstructor
class DemoEditConfirmGetContext {

  @Getter
  private final ModelAndView modelAndView;

  private final DemoForm demoForm;

  private final Integer demoId;

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  void setModelAndView() {
    Category category = demoForm.categoryId() != null ?
        demoFacadeDBLogic.fetchCategory(demoForm.categoryId()) : null;
    Prefecture prefecture = demoForm.prefectureId() != null ?
        demoFacadeDBLogic.fetchPrefecture(demoForm.prefectureId()) : null;

    this.modelAndView.addObject("demoForm", this.demoForm);
    this.modelAndView.addObject("category", category);
    this.modelAndView.addObject("prefecture", prefecture);
    this.modelAndView.addObject("demoId", demoId);
    this.modelAndView.setViewName("demo/demoEditConfirm");
  }

}
