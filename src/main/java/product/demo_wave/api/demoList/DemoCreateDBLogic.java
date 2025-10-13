package product.demo_wave.api.demoList;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.api.demoList.DemoRequest;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.entity.Category;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.Prefecture;
import product.demo_wave.entity.User;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.repository.CategoryRepository;
import product.demo_wave.repository.PrefectureRepository;

@Component
@AllArgsConstructor
public class DemoCreateDBLogic {

//	private static final Logger logger = Logger.getLogger(DemoListDBLogic.class.getSimpleName());

	private final DemoRepository demoRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final PrefectureRepository prefectureRepository;

	/**
	 *
	 */
	@CustomRetry
	Demo saveDemo(DemoRequest request, String supabaseUid) {
		Demo newDemo = demoRepository.saveAndFlush(toEntity(request, supabaseUid));
		return newDemo;
	}

	private Demo toEntity(DemoRequest request, String supabaseUid) {
		Demo demo = new Demo();

		demo.setTitle(request.getTitle());
		demo.setContent(request.getContent());
		demo.setDemoPlace(request.getDemoPlace());
		demo.setPrefecture(fetchPrefecture(request.getPrefectureId()));
		demo.setDemoAddressLatitude(request.getDemoAddressLatitude());
		demo.setDemoAddressLongitude(request.getDemoAddressLongitude());
		demo.setDemoStartDate(request.getDemoStartDate());
		demo.setDemoEndDate(request.getDemoEndDate());
		demo.setCategory(fetchCategory(request.getCategoryId()));
		demo.setUser(fetchUser(supabaseUid));

		return demo;
	}

	@CustomRetry
	Category fetchCategory(Integer categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.orElse(new Category());
	}

	User fetchUser(String supabaseUid) {
		Optional<User> user = userRepository.findBySupabaseUid(supabaseUid);
		return user.orElse(new User());
	}

	@CustomRetry
	Prefecture fetchPrefecture(Integer prefectureId) {
		Optional<Prefecture> prefecture = prefectureRepository.findById(prefectureId);
		return prefecture.orElse(new Prefecture());
	}

}
