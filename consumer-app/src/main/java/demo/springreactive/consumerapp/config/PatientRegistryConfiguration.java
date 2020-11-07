package demo.springreactive.consumerapp.config;

import demo.springreactive.consumerapp.config.endpoint.PatientRegistryEndpoint;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("patient-registry")
public class PatientRegistryConfiguration {

	private String host;
	private PatientRegistryEndpoint endpoint;

}
