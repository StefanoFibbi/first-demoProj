package demo.springreactive.patientregistry.repository;

import demo.springreactive.patientregistry.model.ClinicalDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ClinicalDocumentRepository extends ReactiveMongoRepository<ClinicalDocument, String> {

	Flux<ClinicalDocument> findAllByPatientId(String patientId);

}
