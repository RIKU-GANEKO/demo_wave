package product.demo_wave.user;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UserCompleteGetContext {
    private final UserForm userForm;

    @Getter
    private final ModelAndView modelAndView;

    void setModelAndView() {
        this.modelAndView.addObject("userForm", this.userForm);
        this.modelAndView.setViewName("userComplete");
    }
}
