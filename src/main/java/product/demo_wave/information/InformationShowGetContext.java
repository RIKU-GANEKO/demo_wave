package product.demo_wave.information;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import product.demo_wave.entity.Comment;
import product.demo_wave.entity.Information;

@RequiredArgsConstructor
class InformationShowGetContext {
    private final Integer informationId;
    private Information information;
    private List<Comment> comments;

    private Boolean isParticipant;

    @Getter
    private final ModelAndView mv;

    @Setter
    private InformationFacadeDBLogic informationFacadeDBLogic;

    void fetchInformation() throws UnsupportedOperationException {
        this.information = informationFacadeDBLogic.fetchInformation(this.informationId);
    }

    void fetchComment() throws UnsupportedOperationException {
        this.comments = informationFacadeDBLogic.fetchComment(this.informationId);
    }

    void fetchIsParticipant() {
        isParticipant = informationFacadeDBLogic.isParticipant(informationId);
    }

    void setModelAndView() {
        this.mv.addObject("information", this.information);
        this.mv.addObject("isParticipant", this.isParticipant);
        this.mv.addObject("comments", this.comments);
        this.mv.setViewName("information");
    }
}
