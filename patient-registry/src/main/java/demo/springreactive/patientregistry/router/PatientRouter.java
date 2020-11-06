package demo.springreactive.patientregistry.router;

import demo.springreactive.patientregistry.router.handler.PatientHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Profile("func")
@Configuration
public class PatientRouter {
	private static final String BASE_ROUTE = "/v1/patients";
	private static final String ROUTE_PATIENT_ID = BASE_ROUTE + "/{id}";

	@Bean
	RouterFunction<ServerResponse> patientRoutes(PatientHandler handler) {
		return route(GET(BASE_ROUTE), handler::findAll)
				.andRoute(POST(BASE_ROUTE), handler::insertPatient)
				.andRoute(DELETE(BASE_ROUTE), handler::deleteAllPatients)

				.andRoute(GET(ROUTE_PATIENT_ID), handler::findPatientById)
				.andRoute(PUT(ROUTE_PATIENT_ID), handler::updatePatient)
				.andRoute(DELETE(ROUTE_PATIENT_ID), handler::deletePatient)
				;
	}
}
