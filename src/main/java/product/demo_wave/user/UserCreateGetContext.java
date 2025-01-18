package product.demo_wave.user;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class UserCreateGetContext {
//    private final Integer accountId;

    private final UserForm userForm;

    @Getter
    private final ModelAndView modelAndView;

    @Setter
    private UserFacadeDBLogic userFacadeDBLogic;

//    private Account account;

//    void init() {
//        account = userFacadeDBLogic.fetchAccount(accountId);
//    }

    void setModelAndView() {
//        modelAndView.addObject("accountId", accountId);
//        modelAndView.addObject("accountName", account.getName());
        modelAndView.addObject("userForm", userForm);
        modelAndView.setViewName("signup");
    }
}
