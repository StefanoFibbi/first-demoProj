package demo.springreactive.consumerapp.config.endpoint;

import lombok.Data;

@Data
public class PatientRegistryEndpoint {
	private String allPatients;
	private String allPatientDocuments;
	private String allPatientContacts;
}
