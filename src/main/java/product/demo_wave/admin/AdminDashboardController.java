package product.demo_wave.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理者ダッシュボードコントローラー
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    /**
     * 管理者ダッシュボード
     */
    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        return "admin/dashboard";
    }
}
