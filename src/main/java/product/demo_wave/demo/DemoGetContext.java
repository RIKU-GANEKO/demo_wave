package product.demo_wave.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import product.demo_wave.AppProperties;

import product.demo_wave.common.logic.BasicFacadeDBLogic.PageData;
import product.demo_wave.logic.GetUserLogic;

@RequiredArgsConstructor
class DemoGetContext {
    private final AppProperties properties;
    private final GetUserLogic getUserLogic; // GetUserLogicをフィールドとして追加

    @Getter
    private final ModelAndView mv;

    @NonNull
    private Pageable pageable;

    @Setter
    private DemoFacadeDBLogic demoFacadeDBLogic;

    private Page<DemoWithParticipantDTO> demoList;

    private String organizerUserName;

    private Integer participantCount;

    void init() {
        if (!pageable.isPaged()) {
            pageable = PageRequest.of(0, properties.getPageRecordsLimit(), pageable.getSort());
        }
    }

    void getOrganizerUserName() {
        this.organizerUserName = getUserLogic.getUser().getName();
    }

//    void getParticipantCount() {
//        this.participantCount = DemoFacadeDBLogic.fetchParticipantCount();
//    }

    void fetchDemo() throws UnsupportedOperationException {
        PageData<DemoWithParticipantDTO> demoPageData = demoFacadeDBLogic.fetchAllDemo(this.pageable);
        this.demoList = demoPageData.getPage();
    }

    void setModelAndView() {
        this.mv.addObject("demoList", this.demoList);
        this.mv.addObject("organizerUserName", this.organizerUserName);
        this.mv.setViewName("demoList");
    }
}
