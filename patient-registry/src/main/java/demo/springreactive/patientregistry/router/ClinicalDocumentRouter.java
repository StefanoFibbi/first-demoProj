package demo.springreactive.patientregistry.router;

import demo.springreactive.patientregistry.router.handler.ClinicalDocumentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Profile("func")
@Configuration
public class ClinicalDocumentRouter {

	private static final String BASE_ROUTE = "/v1/clinical-docs";
	private static final String ROUTE_DOC_ID = BASE_ROUTE + "/{id}";

	@Bean
	RouterFunction<ServerResponse> clinicalDocumentRoutes(ClinicalDocumentHandler handler) {
		return route(GET(BASE_ROUTE), handler::findAll)
				.andRoute(DELETE(BASE_ROUTE), handler::deleteAll)

				.andRoute(GET(ROUTE_DOC_ID), handler::findById)
				;
	}

}
