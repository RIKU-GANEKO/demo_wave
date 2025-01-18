package product.demo_wave.mypage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import product.demo_wave.entity.Account;
import product.demo_wave.entity.Information;
import product.demo_wave.entity.User;

@RequiredArgsConstructor
class MypageGetUserContext {

    @Setter
    private MypageFacadeDBLogic mypageFacadeDBLogic;

    @Getter
    private final ModelAndView mv;

    private User user;
    private Account account;
    private List<Information> participatedInformations;
    private BigDecimal sendDonateAmount;

    void fetchUser() throws UnsupportedOperationException {
        user = mypageFacadeDBLogic.fetchUser();
    }

    void fetchAccount() throws UnsupportedOperationException {
        account = mypageFacadeDBLogic.fetchAccount();
    }

    void fetchParticipatedInformation() throws UnsupportedOperationException {
        participatedInformations = mypageFacadeDBLogic.fetchParticipatedInformation();
    }

    void fetchSendDonateAmount() throws UnsupportedOperationException {
        sendDonateAmount = mypageFacadeDBLogic.fetchSendDonateAmount();
    }

    void setModelAndView() {
        this.mv.addObject("user", this.user);
        this.mv.addObject("account", this.account);
        this.mv.addObject("participatedInformations", this.participatedInformations);
        this.mv.addObject("sendDonateAmount", this.sendDonateAmount);
        this.mv.setViewName("mypage");
    }
}
