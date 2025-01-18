package product.demo_wave.information;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class InformationCreateConfirmGetContext {

  private final InformationForm informationForm;

  @Getter
  private final ModelAndView modelAndView;

  void setModelAndView() {
    this.modelAndView.addObject(informationForm);
    this.modelAndView.setViewName("informationCreateConfirm");
  }

}
