package demo.springreactive.patientregistry.router.handler;

import demo.springreactive.patientregistry.model.Patient;
import demo.springreactive.patientregistry.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static demo.springreactive.patientregistry.router.util.RequestUtil.id;

@Slf4j
@Component
public class PatientHandler {

	PatientService patientService;

	@Autowired
	public PatientHandler(PatientService patientService) {
		this.patientService = patientService;
	}

	public Mono<ServerResponse> findPatientById(ServerRequest request) {
		log.info("{}", request);

		return this.patientService.get(id(request))
				.flatMap(p -> ServerResponse.ok().bodyValue(p))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> findAll(ServerRequest request) {
		log.info("{}", request);
		return this.buildDefaultResponse(this.patientService.getAll());
	}

	public Mono<ServerResponse> insertPatient(ServerRequest request) {
		log.info("{}", request);
		Mono<Patient> patientMono = request
				.bodyToMono(Patient.class)
				.flatMap(patient -> this.patientService.create(patient.getFirstName(), patient.getLastName()));

		return this.buildDefaultResponse(patientMono);
	}

	public Mono<ServerResponse> deletePatient(ServerRequest request) {
		log.info("{}", request);
		return this.buildDefaultResponse(this.patientService.deleteById(id(request)));
	}

	public Mono<ServerResponse> deleteAllPatients(ServerRequest request) {
		log.info("{}", request);
		return this.buildDefaultResponse(this.patientService.deleteAll());
	}

	public Mono<ServerResponse> updatePatient(ServerRequest request) {
		log.info("{}", request);
		String patientId = id(request);
		Mono<Patient> updatedPat = request
				.bodyToMono(Patient.class)
				.flatMap(patient -> this.patientService.update(patientId, patient.getFirstName(), patient.getLastName()));

		return this.buildDefaultResponse(updatedPat);
	}

	private Mono<ServerResponse> buildDefaultResponse(Mono<?> pubPatient) {
		return pubPatient
				.flatMap(pat -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(pat)
				)
				.switchIfEmpty(ServerResponse.noContent().build());
	}

	private Mono<ServerResponse> buildDefaultResponse(Flux<?> pubPatient) {
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(pubPatient, Patient.class);
	}
}
