package product.demo_wave.lp;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import product.demo_wave.demo.DemoWithParticipantDTO;
import product.demo_wave.entity.Category;
import product.demo_wave.entity.Prefecture;
import product.demo_wave.entity.User;
import product.demo_wave.repository.CategoryRepository;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.PrefectureRepository;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.security.SupabaseUserDetails;

@Controller
@AllArgsConstructor
public class LpController {
    private final DemoRepository demoRepository;
    private final CategoryRepository categoryRepository;
    private final PrefectureRepository prefectureRepository;
    private final UserRepository userRepository;

    /**
     * 認証情報を取得してModelAndViewに追加する共通メソッド
     */
    private void addLoggedInUser(ModelAndView mv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
            !"anonymousUser".equals(authentication.getPrincipal())) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof SupabaseUserDetails) {
                SupabaseUserDetails supabaseUser = (SupabaseUserDetails) principal;
                // Supabase UIDからユーザー情報を取得
                User user = userRepository.findById(java.util.UUID.fromString(supabaseUser.getSupabaseUserId())).orElse(null);
                if (user != null) {
                    mv.addObject("loggedInUser", user);
                }
            }
        }
    }

    @GetMapping("/")
    public ModelAndView home(ModelAndView mv) {
        // 認証情報を取得
        addLoggedInUser(mv);
        // 開催中・開催予定のデモを優先して上位9件を取得（人気のデモ）
        Pageable topPageable = PageRequest.of(0, 9);
        List<DemoWithParticipantDTO> popularDemos = demoRepository.findDemosByPopularityPrioritizingUpcoming(topPageable);

        // すべてのデモを取得（開催中・開催予定を優先、24件）
        Pageable allPageable = PageRequest.of(0, 24);
        List<DemoWithParticipantDTO> allDemos = demoRepository.findDemosByPopularityPrioritizingUpcoming(allPageable);

        mv.addObject("popularDemos", popularDemos);
        mv.addObject("allDemos", allDemos);
        mv.setViewName("home");
        return mv;
    }

    @GetMapping("/about")
    public ModelAndView about(ModelAndView mv) {
        addLoggedInUser(mv);
        mv.setViewName("about");
        return mv;
    }

    @GetMapping("/organizer-guide")
    public ModelAndView organizerGuide(ModelAndView mv) {
        addLoggedInUser(mv);
        mv.setViewName("organizerGuide");
        return mv;
    }

    @GetMapping("/privacy")
    public ModelAndView privacy(ModelAndView mv) {
        addLoggedInUser(mv);
        mv.setViewName("privacy");
        return mv;
    }

    @GetMapping("/terms")
    public ModelAndView terms(ModelAndView mv) {
        addLoggedInUser(mv);
        mv.setViewName("terms");
        return mv;
    }

    @GetMapping("/search")
    public ModelAndView search(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer prefectureId,
            @RequestParam(required = false) String prefectureName,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "newest") String sort,
            ModelAndView mv) {

        // 認証情報を取得
        addLoggedInUser(mv);

        // カテゴリー名からIDを取得
        if (categoryName != null && categoryId == null) {
            categoryId = categoryRepository.findByName(categoryName)
                    .map(Category::getId)
                    .orElse(null);
        }

        // 都道府県名からIDを取得
        if (prefectureName != null && prefectureId == null) {
            prefectureId = prefectureRepository.findByName(prefectureName)
                    .map(Prefecture::getId)
                    .orElse(null);
        }

        // すべてのカテゴリーと都道府県を取得
        List<Category> categories = categoryRepository.findAll();
        List<Prefecture> prefectures = prefectureRepository.findAll();

        // 検索結果を取得
        Pageable pageable = PageRequest.of(0, 100);
        List<DemoWithParticipantDTO> demos;

        // ソート順に応じてデータを取得
        switch (sort) {
            case "donation":
                demos = demoRepository.searchDemosByDonation(categoryId, prefectureId, keyword, pageable);
                break;
            case "participants":
                demos = demoRepository.searchDemosByPopular(categoryId, prefectureId, keyword, pageable);
                break;
            case "newest":
            default:
                demos = demoRepository.searchDemosByNewest(categoryId, prefectureId, keyword, pageable);
                break;
        }

        mv.addObject("demos", demos);
        mv.addObject("categories", categories);
        mv.addObject("prefectures", prefectures);
        mv.addObject("selectedCategoryId", categoryId);
        mv.addObject("selectedPrefectureId", prefectureId);
        mv.addObject("keyword", keyword);
        mv.addObject("sort", sort);
        mv.setViewName("search");
        return mv;
    }
}