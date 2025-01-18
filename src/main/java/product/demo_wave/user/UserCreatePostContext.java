package product.demo_wave.user;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class UserCreatePostContext {
    private final UserForm userForm;
    private final BindingResult bindingResult;

    @Getter
    private final ModelAndView modelAndView;

    private final RedirectAttributes redirectAttributes;

    @Setter
    private UserFacadeDBLogic userFacadeDBLogic;

    /**
     * 入力内容にエラーがあるかどうかを返す
     * @return エラーがある場合はtrue, ない場合はfalse
     */
    boolean hasErrors() {
        boolean isError = this.bindingResult.hasErrors();
        isError = this.isEmailDuplicate() ? true : isError;
        return isError;
    }

    /**
     * メールアドレスが重複しているかどうかを返す
     * @return 重複している場合はtrue, していない場合はfalse
     */
    boolean isEmailDuplicate() {
        if (userFacadeDBLogic.existsSameEmail(this.userForm.email())) {
            this.bindingResult.rejectValue("email", "email.exist", "入力したメールアドレスは登録済みです");
            return true;
        }
        System.out.println("メールアドレス精査中");
        return false;
    }

    void setErrorModelAndView() {
//        modelAndView.addObject("accountId", userForm.accountId());
//        modelAndView.addObject("accountName", userForm.accountName());
        modelAndView.addObject("userForm", userForm);
        modelAndView.setViewName("userCreate");
    }

    void setModelAndView() {
        this.redirectAttributes.addFlashAttribute("userForm", this.userForm);
        this.modelAndView.setViewName("redirect:/user/create/confirm");
    }
}
