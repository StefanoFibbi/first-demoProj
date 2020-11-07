package demo.springreactive.patientregistry.router.handler;

import demo.springreactive.patientregistry.model.Patient;
import demo.springreactive.patientregistry.service.ClinicalDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class ClinicalDocumentHandler {

	ClinicalDocumentService documentService;

	@Autowired
	public ClinicalDocumentHandler(ClinicalDocumentService documentService) {
		this.documentService = documentService;
	}

	public Mono<ServerResponse> findAll(ServerRequest request) {
		log.info("{}", request);

		return this.buildDefaultResponse(
				request
						.queryParam("patient-id")
						.map(p -> this.documentService.getAllByPatientId(p))
						.orElse(this.documentService.getAll())
		);
	}

	public Mono<ServerResponse> findById(ServerRequest request) {
		log.info("{}", request);
		String docId = request.pathVariable("documentId");
		return this.documentService.get(docId)
				.flatMap(doc -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(doc))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> deleteAll(ServerRequest request) {
		log.info("{}", request);
		return this.buildDefaultResponse(this.documentService.deleteAll());
	}

	private Mono<ServerResponse> buildDefaultResponse(Mono<?> pubDocument) {
		return pubDocument
				.flatMap(pat -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(pat)
				)
				.switchIfEmpty(ServerResponse.noContent().build());
	}

	private Mono<ServerResponse> buildDefaultResponse(Flux<?> pubDocument) {
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(pubDocument, Patient.class);
	}

}
