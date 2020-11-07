package demo.springreactive.consumerapp.model.patientregistry;

import lombok.Data;

@Data
public class ContactInfo {
	private String id;
	private String email;
	private String patientId;
	private String phoneNum;
	private String postalAddress;
}
