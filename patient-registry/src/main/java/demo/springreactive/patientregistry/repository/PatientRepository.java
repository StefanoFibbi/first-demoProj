package demo.springreactive.patientregistry.repository;

import demo.springreactive.patientregistry.model.Patient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PatientRepository extends ReactiveMongoRepository<Patient, String> {
}
