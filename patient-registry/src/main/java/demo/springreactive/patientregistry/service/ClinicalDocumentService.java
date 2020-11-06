package demo.springreactive.patientregistry.service;

import java.util.concurrent.atomic.AtomicInteger;

import demo.springreactive.patientregistry.event.ClinicalDocumentCreatedEvent;
import demo.springreactive.patientregistry.model.ClinicalDocument;
import demo.springreactive.patientregistry.repository.ClinicalDocumentRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ClinicalDocumentService {

	AtomicInteger createdDocumentCount;
	ApplicationEventPublisher eventPublisher;
	ClinicalDocumentRepository repository;

	@Autowired
	public ClinicalDocumentService(ApplicationEventPublisher eventPublisher, ClinicalDocumentRepository repository) {
		this.repository = repository;
		this.eventPublisher = eventPublisher;
		this.createdDocumentCount = new AtomicInteger();
	}

	@SneakyThrows
	public Flux<ClinicalDocument> getAll() {
		return this.repository.findAll();
	}

	public Mono<ClinicalDocument> get(String id) {
		return this.repository.findById(id);
	}

	public Flux<ClinicalDocument> getAllByPatientId(String patientId) {
		return this.repository.findAllByPatientId(patientId);
	}

	public Mono<Void> deleteAll() {
		return this.repository.deleteAll();
	}

	public Mono<ClinicalDocument> create(String patientId, String title) {
		String defDocumentBody = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
		return this.create(patientId, title, defDocumentBody);
	}

	public Mono<ClinicalDocument> create(String patientId, String title, String docBody) {
		return this.repository
				.save(new ClinicalDocument(
						"DOC_" + this.createdDocumentCount.incrementAndGet(),
						patientId,
						title,
						docBody
				))
				.doOnSuccess(patient -> this.eventPublisher.publishEvent(new ClinicalDocumentCreatedEvent(patient)));
	}

	public Mono<ClinicalDocument> deleteById(String id) {
		return this.repository
				.findById(id)
				.flatMap(p -> this.repository.deleteById(p.getId()).thenReturn(p));
	}

}
