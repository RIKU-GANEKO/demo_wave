package product.demo_wave.information;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class InformationCreateGetContext {

  @Getter
  private final ModelAndView modelAndView;

  private final InformationForm informationForm;

  void setModelAndView() {
    this.modelAndView.addObject("informationForm", this.informationForm);
    this.modelAndView.setViewName("informationCreate");
  }

}
