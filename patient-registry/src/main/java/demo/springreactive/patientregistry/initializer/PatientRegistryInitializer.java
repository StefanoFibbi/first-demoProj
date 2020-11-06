package demo.springreactive.patientregistry.initializer;

import java.util.concurrent.atomic.AtomicInteger;

import demo.springreactive.patientregistry.service.ClinicalDocumentService;
import demo.springreactive.patientregistry.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class PatientRegistryInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final PatientService patientService;
	private final ClinicalDocumentService documentService;
	AtomicInteger patientCounter;

	@Autowired
	public PatientRegistryInitializer(PatientService patientService, ClinicalDocumentService documentService) {
		this.patientCounter = new AtomicInteger();
		this.patientService = patientService;
		this.documentService = documentService;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		this.patientService
				.deleteAll()
				.thenMany(Flux.just("John Snow", "Arya Stark", "Peter Parker", "Ezio Auditore")
						.flatMap(n -> this.patientService.create(n, this.buildEmailByName(n)))
				)
				.then(this.documentService.deleteAll())
				.thenMany(patientService.getAll())
				.flatMap(patient -> {
					log.info("Saved patient: {}", patient);
					return Flux.just("Blood report", "Test exam")
							.flatMap(title -> this.documentService.create(patient.getId(), title));
				})
				.thenMany(this.documentService.getAll())
				.subscribe(doc -> log.info("Saved document: {}", doc));
	}

	private String buildEmailByName(String name) {
		return (StringUtils.replace(name, " ", "") + "@email.com").toLowerCase();
	}

}
