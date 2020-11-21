package demo.springreactive.consumerapp.model.patientregistry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo {
	private String id;
	private String email;
	private String patientId;
	private String phoneNum;
	private String postalAddress;
}
