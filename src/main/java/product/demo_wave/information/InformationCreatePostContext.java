package product.demo_wave.information;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class InformationCreatePostContext {

  @Getter
  private final ModelAndView modelAndView;
  private final InformationForm informationForm;
  private final BindingResult result;
  private final RedirectAttributes redirectAttributes;

  boolean hasErrors() {
    return this.result.hasErrors();
  }

  void setErrorModelAndView() {
    this.modelAndView.setViewName("informationCreate");
  }

  void setModelAndView() {
    this.redirectAttributes.addFlashAttribute("informationForm", informationForm);
    this.modelAndView.setViewName("redirect:/information/create/confirm");
  }
}
