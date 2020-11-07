package demo.springreactive.patientregistry.router.handler;

import demo.springreactive.patientregistry.model.ContactInfo;
import demo.springreactive.patientregistry.service.ContactInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ContactInfoHandler {

	ContactInfoService contactInfoService;

	@Autowired
	public ContactInfoHandler(ContactInfoService contactInfoService) {
		this.contactInfoService = contactInfoService;
	}

	public Mono<ServerResponse> findAll(ServerRequest request) {
		log.info("{}", request);
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(request
						.queryParam("patient-id")
						.map(p -> this.contactInfoService.getAllByPatientId(p))
						.orElse(this.contactInfoService.getAll()), ContactInfo.class);
	}

	public Mono<ServerResponse> deleteAll(ServerRequest request) {
		log.info("{}", request);
		return this.contactInfoService
				.deleteAll()
				.then(ServerResponse.noContent().build());
	}
}
