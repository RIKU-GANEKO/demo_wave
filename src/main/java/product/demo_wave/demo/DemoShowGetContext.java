package product.demo_wave.demo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import product.demo_wave.entity.Comment;
import product.demo_wave.entity.Demo;

@RequiredArgsConstructor
class DemoShowGetContext {
    private final Integer demoId;
    private Demo demo;
    private List<Comment> comments;

    private Boolean isParticipant;
    private Integer participantCount;
    private BigDecimal donateAmount;

    @Getter
    private final ModelAndView mv;

    @Setter
    private DemoFacadeDBLogic demoFacadeDBLogic;

    void fetchDemo() throws UnsupportedOperationException {
        this.demo = demoFacadeDBLogic.fetchDemo(this.demoId);
    }

    void fetchComment() throws UnsupportedOperationException {
        this.comments = demoFacadeDBLogic.fetchComment(this.demoId);
    }

    void fetchIsParticipant() {
        isParticipant = demoFacadeDBLogic.isParticipant(demoId);
    }

    void fetchParticipantCount() {
        participantCount = demoFacadeDBLogic.participantCount(demoId);
    }

    void fetchDonateAmount() {
        donateAmount = demoFacadeDBLogic.donateAmount(demoId);
    }

    void setModelAndView() {
        this.mv.addObject("demo", this.demo);
        this.mv.addObject("isParticipant", this.isParticipant);
        this.mv.addObject("comments", this.comments);
        this.mv.addObject("participantCount", this.participantCount);
        this.mv.addObject("donateAmount", this.donateAmount);
        this.mv.setViewName("demo");
    }
}
