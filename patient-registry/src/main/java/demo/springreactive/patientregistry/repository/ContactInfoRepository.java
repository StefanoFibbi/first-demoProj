package demo.springreactive.patientregistry.repository;

import demo.springreactive.patientregistry.model.ContactInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ContactInfoRepository extends ReactiveMongoRepository<ContactInfo, String> {
	Flux<ContactInfo> getAllByPatientId(String patientId);
}
