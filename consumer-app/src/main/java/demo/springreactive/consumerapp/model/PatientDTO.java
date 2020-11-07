package demo.springreactive.consumerapp.model;

import java.util.ArrayList;
import java.util.List;

import demo.springreactive.consumerapp.model.patientregistry.ClinicalDocument;
import demo.springreactive.consumerapp.model.patientregistry.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
	private String name;
	private String email;
	private List<ClinicalDocumentDTO> clinicalDocuments;

	public PatientDTO(Patient patient) {
		this.name = patient.getFullName();
		this.email = patient.getEmail();
		this.clinicalDocuments = new ArrayList<>();
	}

	public PatientDTO(Patient patient, List<ClinicalDocument> clinicalDocuments) {
		this(patient);
		clinicalDocuments.forEach(doc -> this.clinicalDocuments.add(new ClinicalDocumentDTO(doc)));
	}
}
