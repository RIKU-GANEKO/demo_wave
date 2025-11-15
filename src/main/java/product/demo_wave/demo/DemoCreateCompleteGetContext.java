package product.demo_wave.demo;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import product.demo_wave.entity.Category;
import product.demo_wave.entity.Prefecture;

@RequiredArgsConstructor
class DemoCreateCompleteGetContext {

  private final DemoForm demoForm;

  @Getter
  private final ModelAndView modelAndView;

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  void setModelAndView() {
    // カテゴリーと都道府県の情報を取得
    Category category = demoForm.categoryId() != null ?
        demoFacadeDBLogic.fetchCategory(demoForm.categoryId()) : null;
    Prefecture prefecture = demoForm.prefectureId() != null ?
        demoFacadeDBLogic.fetchPrefecture(demoForm.prefectureId()) : null;

    this.modelAndView.addObject("demoForm", demoForm);
    this.modelAndView.addObject("category", category);
    this.modelAndView.addObject("prefecture", prefecture);
    this.modelAndView.setViewName("demo/demoCreateComplete");
  }
}
