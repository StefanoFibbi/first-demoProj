package demo.springreactive.consumerapp.service;

import java.util.List;

import demo.springreactive.consumerapp.config.PatientRegistryConfiguration;
import demo.springreactive.consumerapp.model.PatientDTO;
import demo.springreactive.consumerapp.model.patientregistry.ClinicalDocument;
import demo.springreactive.consumerapp.model.patientregistry.ContactInfo;
import demo.springreactive.consumerapp.model.patientregistry.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ReactivePatientConsumerService {

	private final WebClient webClient;
	private final PatientRegistryConfiguration registryConfig;

	public ReactivePatientConsumerService(PatientRegistryConfiguration registryConfig, @Qualifier("patient-registry") WebClient webClient) {
		this.registryConfig = registryConfig;
		this.webClient = webClient;
	}

	public Flux<PatientDTO> getAllPatients() {
		return this.webClient
				.get()
				.uri(this.registryConfig.getEndpoint().getAllPatients())
				.retrieve()
				.bodyToFlux(Patient.class)
				.flatMapSequential(patient -> {
					log.info("Request documents for patient {}", patient.getId());
					Mono<List<ClinicalDocument>> docsMono =
							this.webClient
									.get()
									.uri(this.registryConfig.getEndpoint().getAllPatientDocuments(), patient.getId())
									.retrieve()
									.bodyToFlux(ClinicalDocument.class)
									.collectList()
									.doOnNext(docList -> log.info("Documents of patient {}: {}", patient.getId(), docList));

					log.info("Request contacts for patient {}", patient.getId());
					Mono<List<ContactInfo>> contactsMono =
							this.webClient
									.get()
									.uri(this.registryConfig.getEndpoint().getAllPatientContacts(), patient.getId())
									.retrieve()
									.bodyToFlux(ContactInfo.class)
									.collectList()
									.doOnNext(contacts -> log.info("Contacts of patient {}: {}", patient.getId(), contacts));

					return Mono.zip(docsMono, contactsMono, (docs, contacts) ->
							new PatientDTO(patient, docs, contacts
									.stream()
									.findFirst()
									.orElse(null)));
				});
	}
}
