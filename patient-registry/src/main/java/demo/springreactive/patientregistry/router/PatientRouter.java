package demo.springreactive.patientregistry.router;

import demo.springreactive.patientregistry.router.handler.PatientHandler;
import demo.springreactive.patientregistry.router.util.DelayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

	@Profile("func")
	@Configuration
	public class PatientRouter {
		private static final String BASE_ROUTE = "/v1/patients";
		private static final String ROUTE_PATIENT_ID = BASE_ROUTE + "/{id}";

		@Bean
		RouterFunction<ServerResponse> patientRoutes(PatientHandler handler) {
			return RouterFunctions.route()
					.GET(BASE_ROUTE, handler::findAll)
					.POST(BASE_ROUTE, handler::insertPatient)
					.DELETE(BASE_ROUTE, handler::deleteAllPatients)

					.GET(ROUTE_PATIENT_ID, handler::findPatientById)
					.PUT(ROUTE_PATIENT_ID, handler::updatePatient)
					.DELETE(ROUTE_PATIENT_ID, handler::deletePatient)

					.filter(new DelayFilter())
					.build();
		}
	}
