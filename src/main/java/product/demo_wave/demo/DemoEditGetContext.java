package product.demo_wave.demo;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import product.demo_wave.entity.Category;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.Prefecture;

@RequiredArgsConstructor
class DemoEditGetContext {

  @Getter
  private final ModelAndView modelAndView;

  private final Integer demoId;

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  void setModelAndView() {
    // デモ情報を取得
    Demo demo = demoFacadeDBLogic.fetchDemo(demoId);

    // DemoFormに変換
    DemoForm demoForm = DemoForm.fromEntity(demo);

    // カテゴリと都道府県の一覧を取得
    List<Category> categories = demoFacadeDBLogic.fetchAllCategories();
    List<Prefecture> prefectures = demoFacadeDBLogic.fetchAllPrefectures();

    // 編集可否判定
    boolean canEditLocationAndTime = demoFacadeDBLogic.canEditLocationAndTime(demoId);

    this.modelAndView.addObject("demoForm", demoForm);
    this.modelAndView.addObject("categories", categories);
    this.modelAndView.addObject("prefectures", prefectures);
    this.modelAndView.addObject("canEditLocationAndTime", canEditLocationAndTime);
    this.modelAndView.addObject("demoId", demoId);
    this.modelAndView.setViewName("demo/demoEdit");
  }

}
