package demo.springreactive.patientregistry.service;

import java.util.concurrent.atomic.AtomicInteger;

import demo.springreactive.patientregistry.event.PatientCreatedEvent;
import demo.springreactive.patientregistry.model.Patient;
import demo.springreactive.patientregistry.repository.PatientRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PatientService {

	AtomicInteger createdPatientCount;
	ApplicationEventPublisher eventPublisher;
	PatientRepository repository;

	@Autowired
	public PatientService(ApplicationEventPublisher eventPublisher, PatientRepository repository) {
		this.repository = repository;
		this.eventPublisher = eventPublisher;
		this.createdPatientCount = new AtomicInteger();
	}

	@SneakyThrows
	public Flux<Patient> getAll() {
		return this.repository.findAll();
	}

	public Mono<Patient> get(String id) {
		return this.repository.findById(id);
	}

	@SneakyThrows
	public Mono<Patient> update(String id, String firstName, String lastName) {
		return this.repository
				.findById(id)
				.map(p -> new Patient(p.getId(), firstName, lastName))
				.flatMap(this.repository::save);
	}

	public Mono<Void> deleteById(String id) {
		return this.repository
				.findById(id)
				.flatMap(p -> this.repository.deleteById(p.getId()));
	}

	public Mono<Void> deleteAll() {
		return this.repository.deleteAll();
	}

	public Mono<Patient> create(String fullName) {
		String[] nameSegments = fullName.split(" ");
		return this.create(nameSegments[0], nameSegments[1]);
	}

	public Mono<Patient> create(String firstName, String lastName) {
		return this.repository
				.save(new Patient(
						"PAT_" + this.createdPatientCount.incrementAndGet(),
						firstName,
						lastName
				))
				.doOnSuccess(patient -> this.eventPublisher.publishEvent(new PatientCreatedEvent(patient)));
	}

}
