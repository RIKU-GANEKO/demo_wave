package product.demo_wave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@EnableJpaAuditing
//@EnableJpaRepositories(basePackages = "jp.fb.freepass.hbmanager.common.persistence.repository")
//@EntityScan(basePackages = "jp.fb.freepass.hbmanager.common.persistence.entity")
@EnableRetry
@SpringBootApplication
public class DemoWaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWaveApplication.class, args);
	}

}
