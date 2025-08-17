package product.demo_wave.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class SupabaseConfigController {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.anon.key}")
    private String supabaseAnonKey;

    @Value("${google.oauth.client.id}")
    private String googleOAuthClientId;

    @GetMapping("/supabase")
    public Map<String, String> getSupabaseConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("url", supabaseUrl);
        config.put("anonKey", supabaseAnonKey);
        config.put("googleClientId", googleOAuthClientId);
        return config;
    }
}