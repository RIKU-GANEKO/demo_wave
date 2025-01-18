package product.demo_wave.user;

import java.util.NoSuchElementException;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class UserCreateConfirmPostContext {
    private final UserForm userForm;

    @Getter
    private final ModelAndView modelAndView;

    private final RedirectAttributes redirectAttributes;

    @Setter
    private UserFacadeDBLogic userFacadeDBLogic;

    void saveUser() throws NoSuchElementException {
        userFacadeDBLogic.saveUser(this.userForm);
    }

    void setModelAndView() {
        this.redirectAttributes.addFlashAttribute("userForm", this.userForm);
        this.modelAndView.setViewName("redirect:/user/create/complete");
    }
}
