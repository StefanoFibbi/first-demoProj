package demo.springreactive.patientregistry.service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import demo.springreactive.patientregistry.model.ContactInfo;
import demo.springreactive.patientregistry.repository.ContactInfoRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ContactInfoService {

	AtomicInteger createdDocumentCount;
	ContactInfoRepository repository;
	ApplicationEventPublisher eventPublisher;

	@Autowired
	public ContactInfoService(ApplicationEventPublisher eventPublisher, ContactInfoRepository repository) {
		this.repository = repository;
		this.eventPublisher = eventPublisher;
		this.createdDocumentCount = new AtomicInteger();
	}

	@SneakyThrows
	public Flux<ContactInfo> getAll() {
		return this.repository.findAll();
	}

	public Mono<ContactInfo> getAllByPatientId(String patientId) {
		return this.repository.getAllByPatientId(patientId).single();
	}

	public Mono<ContactInfo> get(String id) {
		return this.repository.findById(id);
	}

	public Mono<Void> deleteAll() {
		return this.repository.deleteAll();
	}

	public Mono<ContactInfo> create(String patientId, String email, String phoneNum, String postalAddress) {
		return this.repository.save(
				new ContactInfo()
						.setId(UUID.randomUUID().toString())
						.setPatientId(patientId)
						.setEmail(email)
						.setPhoneNum(phoneNum)
						.setPostalAddress(postalAddress));
	}

}
