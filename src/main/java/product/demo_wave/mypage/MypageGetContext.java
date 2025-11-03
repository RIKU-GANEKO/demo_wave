package product.demo_wave.mypage;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
class MypageGetContext {

    @Getter
    private final ModelAndView mv;

    @Setter
    private java.util.UUID userId;

    void setModelAndView() {
        this.mv.setViewName("redirect:/mypage/" + userId);
    }
}
