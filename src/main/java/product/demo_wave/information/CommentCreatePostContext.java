package product.demo_wave.information;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
class CommentCreatePostContext {

  private final Integer informationId;

  @Setter
  private InformationFacadeDBLogic informationFacadeDBLogic;

  @Getter
  private final ModelAndView modelAndView;
  private final CommentForm commentForm;
  private final RedirectAttributes redirectAttributes;

  void saveComment() {
    informationFacadeDBLogic.saveComment(commentForm, informationId);
  }

  void setModelAndView() {
    String encodedInformationId = URLEncoder.encode(informationId.toString(), StandardCharsets.UTF_8);

    this.redirectAttributes.addFlashAttribute(commentForm);
//    this.modelAndView.setViewName("redirect:/information/show");
    this.modelAndView.setViewName("redirect:/information/show?informationId=" + encodedInformationId);
  }

}
