package product.demo_wave.demo;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import product.demo_wave.entity.Category;
import product.demo_wave.entity.Prefecture;

@RequiredArgsConstructor
class DemoCreatePostContext {

  @Getter
  private final ModelAndView modelAndView;
  private final DemoForm demoForm;
  private final BindingResult result;
  private final RedirectAttributes redirectAttributes;
  private final String back;

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  boolean isBackFromConfirm() {
    return "true".equals(this.back);
  }

  boolean hasErrors() {
    return this.result.hasErrors();
  }

  void setErrorModelAndView() {
    // エラー時もカテゴリ・都道府県の一覧が必要
    List<Category> categories = demoFacadeDBLogic.fetchAllCategories();
    List<Prefecture> prefectures = demoFacadeDBLogic.fetchAllPrefectures();

    this.modelAndView.addObject("demoForm", this.demoForm);
    this.modelAndView.addObject("categories", categories);
    this.modelAndView.addObject("prefectures", prefectures);
    this.modelAndView.setViewName("demo/demoCreate");
  }

  void setBackModelAndView() {
    // 確認画面から戻る場合は、フォームデータを保持して作成画面を表示
    List<Category> categories = demoFacadeDBLogic.fetchAllCategories();
    List<Prefecture> prefectures = demoFacadeDBLogic.fetchAllPrefectures();

    this.modelAndView.addObject("demoForm", demoForm);
    this.modelAndView.addObject("categories", categories);
    this.modelAndView.addObject("prefectures", prefectures);
    this.modelAndView.setViewName("demo/demoCreate");
  }

  void setModelAndView() {
    this.redirectAttributes.addFlashAttribute("demoForm", demoForm);
    this.modelAndView.setViewName("redirect:/demo/create/confirm");
  }
}
