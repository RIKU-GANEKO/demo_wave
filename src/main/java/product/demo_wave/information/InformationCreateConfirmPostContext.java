package product.demo_wave.information;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class InformationCreateConfirmPostContext {

  @Setter
  private InformationFacadeDBLogic informationFacadeDBLogic;

  @Getter
  private final ModelAndView modelAndView;
  private final InformationForm informationForm;
  private final RedirectAttributes redirectAttributes;

  void saveInformation() {
    informationFacadeDBLogic.saveInformation(informationForm);
  }

  void setModelAndView() {
    this.redirectAttributes.addFlashAttribute(informationForm);
    this.modelAndView.setViewName("redirect:/information/create/complete");
  }

}
