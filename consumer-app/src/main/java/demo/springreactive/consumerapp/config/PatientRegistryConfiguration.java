package demo.springreactive.consumerapp.config;

import demo.springreactive.consumerapp.config.endpoint.PatientRegistryEndpoint;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@Configuration
@ConfigurationProperties("patient-registry")
public class PatientRegistryConfiguration {

	private int port;
	private String host;
	private PatientRegistryEndpoint endpoint;

	@Bean
	@Qualifier("patient-registry")
	public WebClient patientRegistryWebClient() {
		return WebClient.create(this.host);
	}

}
