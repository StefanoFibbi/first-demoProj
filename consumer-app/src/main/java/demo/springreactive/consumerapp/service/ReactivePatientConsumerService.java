package demo.springreactive.consumerapp.service;

import java.util.List;

import demo.springreactive.consumerapp.config.PatientRegistryConfiguration;
import demo.springreactive.consumerapp.model.PatientDTO;
import demo.springreactive.consumerapp.model.patientregistry.ClinicalDocument;
import demo.springreactive.consumerapp.model.patientregistry.ContactInfo;
import demo.springreactive.consumerapp.model.patientregistry.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactivePatientConsumerService {

	private final WebClient webClient;
	private final PatientRegistryConfiguration registryConfig;

	public ReactivePatientConsumerService(PatientRegistryConfiguration registryConfig) {
		this.registryConfig = registryConfig;
		this.webClient = WebClient.create(registryConfig.getHost());
	}

	public Flux<PatientDTO> getAllPatients() {

		return this.webClient
				.get()
				.uri(this.registryConfig.getEndpoint().getAllPatients())
				.retrieve()
				.bodyToFlux(Patient.class)
				.flatMapSequential(patient -> {
					Mono<List<ClinicalDocument>> docsMono =
							this.webClient
									.get()
									.uri(this.registryConfig.getEndpoint().getAllPatientDocuments(), patient.getId())
									.retrieve()
									.bodyToFlux(ClinicalDocument.class)
									.collectList();

					Mono<List<ContactInfo>> contactsMono =
							this.webClient
									.get()
									.uri(this.registryConfig.getEndpoint().getAllPatientContacts(), patient.getId())
									.retrieve()
									.bodyToFlux(ContactInfo.class)
									.collectList();

					return Mono.zip(docsMono, contactsMono, (docs, contacts) ->
							new PatientDTO(patient, docs, contacts
									.stream()
									.findFirst()
									.orElse(null)));
				});
	}
}
