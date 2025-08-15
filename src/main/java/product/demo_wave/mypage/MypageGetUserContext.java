package product.demo_wave.mypage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import product.demo_wave.entity.Account;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.User;

@RequiredArgsConstructor
class MypageGetUserContext {

    @Setter
    private MypageFacadeDBLogic mypageFacadeDBLogic;

    @Getter
    private final ModelAndView mv;

    @Setter
    private Integer userId;

    private User user;
    private Account account;
    private List<Demo> participatedDemos;
    private BigDecimal sendDonateAmount;

    void fetchUser() throws UnsupportedOperationException {
        user = mypageFacadeDBLogic.fetchUser();
    }

    void fetchAccount() throws UnsupportedOperationException {
        account = mypageFacadeDBLogic.fetchAccount();
    }

    void fetchParticipatedDemo() throws UnsupportedOperationException {
        participatedDemos = mypageFacadeDBLogic.fetchParticipatedDemo();
    }

    void fetchSendDonateAmount() throws UnsupportedOperationException {
        sendDonateAmount = mypageFacadeDBLogic.fetchSendDonateAmount();
    }

    void setModelAndView() {
        this.mv.addObject("user", this.user);
        System.out.println("user: " + user);
        this.mv.addObject("account", this.account);
        System.out.println("account: " + account);
        this.mv.addObject("participatedDemos", this.participatedDemos);
        System.out.println("participatedDemos: " + participatedDemos);
        this.mv.addObject("sendDonateAmount", this.sendDonateAmount);
        System.out.println("sendDonateAmount: " + sendDonateAmount);
        // Add receiveDonateAmount for the template
        this.mv.addObject("receiveDonateAmount", BigDecimal.ZERO); // TODO: implement if needed
        this.mv.setViewName("mypage/mypage");
    }
}
