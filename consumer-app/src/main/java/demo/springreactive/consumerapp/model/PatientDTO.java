package demo.springreactive.consumerapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import demo.springreactive.consumerapp.model.patientregistry.ClinicalDocument;
import demo.springreactive.consumerapp.model.patientregistry.ContactInfo;
import demo.springreactive.consumerapp.model.patientregistry.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "contacts"})
public class PatientDTO {
	@JsonIgnore
	private String firstName;

	@JsonIgnore
	private String lastName;

	private List<ClinicalDocumentDTO> clinicalDocuments;
	private ContactInfoDTO contacts;

	public PatientDTO(Patient patient) {
		this.firstName = patient.getFirstName();
		this.lastName = patient.getLastName();
		this.clinicalDocuments = new ArrayList<>();
	}

	public PatientDTO(Patient patient, List<ClinicalDocument> clinicalDocuments) {
		this(patient);

		if (clinicalDocuments != null) {
			this.clinicalDocuments.addAll(clinicalDocuments
					.stream()
					.map(ClinicalDocumentDTO::new)
					.collect(Collectors.toList()));
		}
	}

	public PatientDTO(Patient patient, List<ClinicalDocument> clinicalDocuments, ContactInfo contacts) {
		this(patient, clinicalDocuments);
		this.contacts = contacts != null ? new ContactInfoDTO(contacts) : null;
	}

	@JsonProperty("name")
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

}
