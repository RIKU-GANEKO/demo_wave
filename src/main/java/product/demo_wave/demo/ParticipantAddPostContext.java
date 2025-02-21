package product.demo_wave.demo;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class ParticipantAddPostContext {

  @Setter
  private DemoFacadeDBLogic demoFacadeDBLogic;

  private final Integer demoId;
  @Getter
  private final ModelAndView modelAndView;
  private final RedirectAttributes redirectAttributes;

//  boolean hasErrors() {
//    return this.result.hasErrors();
//  }

//  void setErrorModelAndView() {
//    this.modelAndView.setViewName("demoCreate");
//  }

  void toggleParticipant() {
    demoFacadeDBLogic.toggleParticipant(demoId);
  }

  void setModelAndView() {
    this.redirectAttributes.addAttribute("demoId", demoId);
    this.modelAndView.setViewName("redirect:/demo/show");
  }
}