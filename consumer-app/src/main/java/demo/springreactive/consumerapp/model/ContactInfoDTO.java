package demo.springreactive.consumerapp.model;

import demo.springreactive.consumerapp.model.patientregistry.ContactInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoDTO {
	private String email;
	private String phoneNum;
	private String postalAddress;

	public ContactInfoDTO(ContactInfo contacts) {
		this.email = contacts.getEmail();
		this.phoneNum = contacts.getPhoneNum();
		this.postalAddress = contacts.getPostalAddress();
	}
}
