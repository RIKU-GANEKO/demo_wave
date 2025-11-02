package product.demo_wave.demo;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import product.demo_wave.entity.Category;
import product.demo_wave.entity.Prefecture;

@RequiredArgsConstructor
class DemoEditPostContext {

  @Getter
  private final ModelAndView modelAndView;

  private final DemoForm demoForm;

  private final Integer demoId;

  private final BindingResult bindingResult;

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  boolean hasErrors() {
    return this.bindingResult.hasErrors();
  }

  void setErrorModelAndView() {
    // エラー時もカテゴリ・都道府県の一覧が必要
    List<Category> categories = demoFacadeDBLogic.fetchAllCategories();
    List<Prefecture> prefectures = demoFacadeDBLogic.fetchAllPrefectures();
    boolean canEditLocationAndTime = demoFacadeDBLogic.canEditLocationAndTime(demoId);

    this.modelAndView.addObject("demoForm", this.demoForm);
    this.modelAndView.addObject("categories", categories);
    this.modelAndView.addObject("prefectures", prefectures);
    this.modelAndView.addObject("canEditLocationAndTime", canEditLocationAndTime);
    this.modelAndView.addObject("demoId", this.demoId);
    this.modelAndView.setViewName("demo/demoEdit");
  }

  void setModelAndView() {
    // 確認画面でもカテゴリ・都道府県の情報が必要
    Category category = demoForm.categoryId() != null ?
        demoFacadeDBLogic.fetchCategory(demoForm.categoryId()) : null;
    Prefecture prefecture = demoForm.prefectureId() != null ?
        demoFacadeDBLogic.fetchPrefecture(demoForm.prefectureId()) : null;

    this.modelAndView.addObject("demoForm", this.demoForm);
    this.modelAndView.addObject("category", category);
    this.modelAndView.addObject("prefecture", prefecture);
    this.modelAndView.addObject("demoId", this.demoId);
    this.modelAndView.setViewName("demo/demoEditConfirm");
  }

}
