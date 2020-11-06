package demo.springreactive.patientregistry.router;

import demo.springreactive.patientregistry.router.handler.ClinicalDocumentHandler;
import demo.springreactive.patientregistry.router.util.DelayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Profile("func")
@Configuration
public class ClinicalDocumentRouter {

	private static final String BASE_ROUTE = "/v1/clinical-docs";
	private static final String ROUTE_DOC_ID = BASE_ROUTE + "/{id}";

	@Bean
	RouterFunction<ServerResponse> clinicalDocumentRoutes(ClinicalDocumentHandler handler) {
		return RouterFunctions.route()
				.GET(BASE_ROUTE, handler::findAll)
				.DELETE(BASE_ROUTE, handler::deleteAll)

				.GET(ROUTE_DOC_ID, handler::findById)

				.filter(new DelayFilter())
				.build();
	}

}
