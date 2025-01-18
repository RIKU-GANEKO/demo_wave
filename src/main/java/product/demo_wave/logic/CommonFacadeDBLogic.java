//package product.demo_wave.logic;
//
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//
//import lombok.AllArgsConstructor;
//
//import jp.fb.freepass.hbmanager.common.persistence.entity.Site;
//import jp.fb.freepass.hbmanager.common.persistence.entity.Ssp;
//import jp.fb.freepass.hbmanager.common.persistence.repository.SiteRepository;
//import jp.fb.freepass.hbmanager.common.persistence.repository.SspRepository;
//import jp.fb.freepass.hbmanager.web.common.annotation.CustomRetry;
//
//@Component
//@AllArgsConstructor
//public class CommonFacadeDBLogic {
//    private final SiteRepository siteRepository;
//    private final SspRepository sspRepository;
//
//    @CustomRetry
//    public List<Site> fetchAllSite() {
//        return siteRepository.findAll();
//    }
//
//    @CustomRetry
//    public List<Ssp> fetchAllSsp() {
//        return sspRepository.findAll();
//    }
//}
