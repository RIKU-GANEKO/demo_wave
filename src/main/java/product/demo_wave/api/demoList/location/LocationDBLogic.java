package product.demo_wave.api.demoList.location;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.entity.LocationLogs;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.User;
import product.demo_wave.repository.LocationLogsRepository;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class LocationDBLogic {

//	private static final Logger logger = Logger.getLogger(DemoListDBLogic.class.getSimpleName());

	private final LocationLogsRepository locationLogsRepository;
	private final DemoRepository demoRepository;
	private final UserRepository userRepository;


	/**
	 *
	 */
	@CustomRetry
	LocationLogs saveLocationLogs(String firebaseUid, LocationRequest request, Boolean isWithinRadius) {
		LocationLogs newLocation = locationLogsRepository.saveAndFlush(toEntity(firebaseUid, request, isWithinRadius));
		return newLocation;
	}

	private LocationLogs toEntity(String firebaseUid, LocationRequest request, Boolean isWithinRadius) {
		LocationLogs location = new LocationLogs();

		location.setUser(fetchUser(firebaseUid));
		location.setDemo(fetchDemo(request.getDemoId()));
		location.setTimestamp(LocalDateTime.now());
		location.setLatitude(request.getLatitude());
		location.setLongitude(request.getLongitude());
		location.setIsWithinRadius(isWithinRadius);

		return location;
	}

	@CustomRetry
	Demo fetchDemo(Integer demoId) {
		Optional<Demo> demo = demoRepository.findById(demoId);
		return demo.orElse(new Demo());
	}

	User fetchUser(String firebaseUid) {
		Optional<User> user = userRepository.findByFirebaseUid(firebaseUid);
		return user.orElse(new User());
	}

}
