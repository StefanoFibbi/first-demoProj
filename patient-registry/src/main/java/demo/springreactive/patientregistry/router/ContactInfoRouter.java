package demo.springreactive.patientregistry.router;

import demo.springreactive.patientregistry.router.handler.ContactInfoHandler;
import demo.springreactive.patientregistry.router.util.DelayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Profile("func")
public class ContactInfoRouter {

	@Bean
	RouterFunction<ServerResponse> patientContactInfoRoutes(ContactInfoHandler handler) {
		return RouterFunctions.route()
				.GET("/v1/contact-info", handler::findAll)
				.DELETE("/v1/contact-info", handler::deleteAll)

				.filter(new DelayFilter())
				.build();
	}

}
