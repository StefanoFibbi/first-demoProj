package demo.springreactive.patientregistry.initializer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import demo.springreactive.patientregistry.model.ClinicalDocument;
import demo.springreactive.patientregistry.model.ContactInfo;
import demo.springreactive.patientregistry.service.ClinicalDocumentService;
import demo.springreactive.patientregistry.service.ContactInfoService;
import demo.springreactive.patientregistry.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PatientRegistryInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final PatientService patientService;
	private final ContactInfoService contactService;
	private final ClinicalDocumentService documentService;
	AtomicInteger patientCounter;

	@Autowired
	public PatientRegistryInitializer(PatientService patientService, ClinicalDocumentService documentService, ContactInfoService contactService) {
		this.patientCounter = new AtomicInteger();
		this.patientService = patientService;
		this.contactService = contactService;
		this.documentService = documentService;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		this.patientService.deleteAll()
				.then(this.documentService.deleteAll())
				.then(this.contactService.deleteAll())
				.thenMany(Flux.just("John Snow", "Arya Stark", "Peter Parker", "Ezio Auditore")
						.flatMap(this.patientService::create)
				)
				.doOnNext(patient -> log.info("Created patient: {}", patient))
				.flatMap(patient -> {
					Mono<List<ClinicalDocument>> docsMono = Flux.just("Blood report", "Test exam")
							.flatMap(title -> this.documentService.create(patient.getId(), title))
							.doOnNext(doc -> log.info("Created document: {}", doc))
							.collectList();

					Mono<ContactInfo> contactMono = this.contactService
							.create(
									patient.getId(),
									this.buildEmailByName(patient.getFirstName(), patient.getLastName()),
									"347 000 0000",
									patient.getFirstName() + patient.getLastName() + " street")
							.doOnNext(info -> log.info("Created contact info: {}", info));

					return Mono.zip(docsMono, contactMono, (docs, contacts) -> {
						Map<String, Object> map = new LinkedHashMap<>();
						map.put("docs", docs);
						map.put("contacts", contacts);
						return map;
					});
				})
				.blockLast();
	}

	private String buildEmailByName(String firstName, String lastName) {
		return (firstName + lastName + "@email.com").toLowerCase();
	}

}
