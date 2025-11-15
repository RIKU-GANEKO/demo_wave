package product.demo_wave.demo;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import product.demo_wave.entity.Category;
import product.demo_wave.entity.Prefecture;

@RequiredArgsConstructor
class DemoCreateConfirmGetContext {

  private final DemoForm demoForm;

  @Getter
  private final ModelAndView modelAndView;

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  void setModelAndView() {
    // Get category and prefecture names for display
    Category category = demoFacadeDBLogic.fetchCategory(demoForm.categoryId());
    Prefecture prefecture = demoFacadeDBLogic.fetchPrefecture(demoForm.prefectureId());

    this.modelAndView.addObject("demoForm", demoForm);
    this.modelAndView.addObject("selectedCategoryName", category.getJaName());
    this.modelAndView.addObject("selectedPrefectureName", prefecture.getName());
    this.modelAndView.setViewName("demo/demoCreateConfirm");
  }

}
