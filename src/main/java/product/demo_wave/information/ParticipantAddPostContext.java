package product.demo_wave.information;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class ParticipantAddPostContext {

  @Setter
  private InformationFacadeDBLogic informationFacadeDBLogic;

  private final Integer informationId;
  @Getter
  private final ModelAndView modelAndView;
  private final RedirectAttributes redirectAttributes;

//  boolean hasErrors() {
//    return this.result.hasErrors();
//  }

//  void setErrorModelAndView() {
//    this.modelAndView.setViewName("informationCreate");
//  }

  void toggleParticipant() {
    informationFacadeDBLogic.toggleParticipant(informationId);
  }

  void setModelAndView() {
    this.redirectAttributes.addAttribute("informationId", informationId);
    this.modelAndView.setViewName("redirect:/information/show");
  }
}