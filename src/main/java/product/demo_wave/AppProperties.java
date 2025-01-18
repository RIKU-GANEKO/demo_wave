package product.demo_wave;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "other.app")
public class AppProperties {

	private String baseUrl;

	private int pageRecordsLimit;

}
