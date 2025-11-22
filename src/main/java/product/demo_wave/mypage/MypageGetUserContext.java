package product.demo_wave.mypage;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.User;
import product.demo_wave.demo.DemoWithParticipantDTO;

@RequiredArgsConstructor
class MypageGetUserContext {

    @Setter
    private MypageFacadeDBLogic mypageFacadeDBLogic;

    @Getter
    private final ModelAndView mv;

    @Setter
    private java.util.UUID userId;

    @Setter
    private String pageType; // "demos", "participated", "favorites", "supported", "settings"

    private User user;
    private List<DemoWithParticipantDTO> participatedDemos;
    private List<DemoWithParticipantDTO> favoriteDemos;
    private List<Demo> supportedDemos;
    private List<SupportedDemoDTO> supportedDemosWithAmount;
    private List<DemoWithParticipantDTO> postedDemos;
    private List<Demo> receivedGiftDemos;
    private List<MonthlyGiftSummaryDTO> monthlyGiftSummaries;
    private Integer userPoints;
    private List<product.demo_wave.entity.PointPurchase> pointPurchaseHistory;

    void fetchUser() throws UnsupportedOperationException {
        user = mypageFacadeDBLogic.fetchUser();
    }

    void fetchParticipatedDemo() throws UnsupportedOperationException {
        participatedDemos = mypageFacadeDBLogic.fetchParticipatedDemosWithParticipant();
    }

    void fetchFavoriteDemos() throws UnsupportedOperationException {
        favoriteDemos = mypageFacadeDBLogic.fetchFavoriteDemosWithParticipant();
    }

    void fetchSupportedDemos() throws UnsupportedOperationException {
        supportedDemos = mypageFacadeDBLogic.fetchSupportedDemos();
    }

    void fetchPostedDemos() throws UnsupportedOperationException {
        postedDemos = mypageFacadeDBLogic.fetchPostedDemosWithParticipant();
    }

    void fetchReceivedGiftDemos() throws UnsupportedOperationException {
        receivedGiftDemos = mypageFacadeDBLogic.fetchReceivedGiftDemos();
    }

    void fetchMonthlyGiftSummaries() throws UnsupportedOperationException {
        monthlyGiftSummaries = mypageFacadeDBLogic.fetchMonthlyGiftSummaries();
    }

    void fetchSupportedDemosWithAmount() throws UnsupportedOperationException {
        supportedDemosWithAmount = mypageFacadeDBLogic.fetchSupportedDemosWithAmount();
    }

    void fetchUserPoints() throws UnsupportedOperationException {
        userPoints = mypageFacadeDBLogic.fetchUserPoints();
    }

    void fetchPointPurchaseHistory() throws UnsupportedOperationException {
        pointPurchaseHistory = mypageFacadeDBLogic.fetchPointPurchaseHistory();
    }

    void setModelAndView() {
        this.mv.addObject("user", this.user);
        System.out.println("user: " + user);
        this.mv.addObject("participatedDemos", this.participatedDemos);
        System.out.println("participatedDemos: " + participatedDemos);
        this.mv.addObject("favoriteDemos", this.favoriteDemos);
        System.out.println("favoriteDemos: " + favoriteDemos);
        this.mv.addObject("supportedDemos", this.supportedDemos);
        System.out.println("supportedDemos: " + supportedDemos);
        this.mv.addObject("postedDemos", this.postedDemos);
        System.out.println("postedDemos: " + postedDemos);
        this.mv.addObject("receivedGiftDemos", this.receivedGiftDemos);
        System.out.println("receivedGiftDemos: " + receivedGiftDemos);
        this.mv.addObject("monthlyGiftSummaries", this.monthlyGiftSummaries);
        System.out.println("monthlyGiftSummaries: " + monthlyGiftSummaries);
        this.mv.addObject("supportedDemosWithAmount", this.supportedDemosWithAmount);
        System.out.println("supportedDemosWithAmount: " + supportedDemosWithAmount);
        this.mv.addObject("userPoints", this.userPoints);
        System.out.println("userPoints: " + userPoints);
        this.mv.addObject("pointPurchaseHistory", this.pointPurchaseHistory);
        System.out.println("pointPurchaseHistory: " + pointPurchaseHistory);
        this.mv.addObject("pageType", this.pageType);

        // ページタイプに応じてビュー名を変更
        if ("participated".equals(pageType)) {
            this.mv.setViewName("mypage/participated");
        } else if ("demos".equals(pageType)) {
            this.mv.setViewName("mypage/demos");
        } else if ("favorites".equals(pageType)) {
            this.mv.setViewName("mypage/favorites");
        } else if ("supported".equals(pageType)) {
            this.mv.setViewName("mypage/supported");
        } else if ("settings".equals(pageType)) {
            this.mv.setViewName("mypage/settings");
        } else {
            this.mv.setViewName("mypage/mypage");
        }
    }
}
