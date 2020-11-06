package demo.springreactive.patientregistry.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalDocument {

	@Id
	private String id;

	private String patientId;
	private String title;
	private String doc;

}
