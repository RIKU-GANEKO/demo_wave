package product.demo_wave.demo;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import product.demo_wave.entity.Category;
import product.demo_wave.entity.Prefecture;

@RequiredArgsConstructor
class DemoCreateGetContext {

  @Getter
  private final ModelAndView modelAndView;

  private final DemoForm demoForm;

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  void setModelAndView() {
    List<Category> categories = demoFacadeDBLogic.fetchAllCategories();
    List<Prefecture> prefectures = demoFacadeDBLogic.fetchAllPrefectures();
    
    this.modelAndView.addObject("demoForm", this.demoForm);
    this.modelAndView.addObject("categories", categories);
    this.modelAndView.addObject("prefectures", prefectures);
    this.modelAndView.setViewName("demo/demoCreate");
  }

}
