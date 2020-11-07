package demo.springreactive.patientregistry.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Accessors(chain = true)
public class ContactInfo {

	@Id
	private String id;

	private String email;
	private String patientId;
	private String phoneNum;
	private String postalAddress;

}
