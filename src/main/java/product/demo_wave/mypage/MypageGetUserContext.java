package product.demo_wave.mypage;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    private List<Demo> participatedDemos;

    void fetchUser() throws UnsupportedOperationException {
        user = mypageFacadeDBLogic.fetchUser();
    }

    void fetchParticipatedDemo() throws UnsupportedOperationException {
        participatedDemos = mypageFacadeDBLogic.fetchParticipatedDemo();
    }

    void setModelAndView() {
        this.mv.addObject("user", this.user);
        System.out.println("user: " + user);
        this.mv.addObject("participatedDemos", this.participatedDemos);
        System.out.println("participatedDemos: " + participatedDemos);
        this.mv.setViewName("mypage/mypage");
    }
}
