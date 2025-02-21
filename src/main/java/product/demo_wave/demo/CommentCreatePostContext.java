package product.demo_wave.demo;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
class CommentCreatePostContext {

  private final Integer demoId;

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  @Getter
  private final ModelAndView modelAndView;
  private final CommentForm commentForm;
  private final RedirectAttributes redirectAttributes;

  void saveComment() {
    demoFacadeDBLogic.saveComment(commentForm, demoId);
  }

  void setModelAndView() {
    String encodeddemoId = URLEncoder.encode(demoId.toString(), StandardCharsets.UTF_8);

    this.redirectAttributes.addFlashAttribute(commentForm);
//    this.modelAndView.setViewName("redirect:/demo/show");
    this.modelAndView.setViewName("redirect:/demo/show?demoId=" + encodeddemoId);
  }

}
