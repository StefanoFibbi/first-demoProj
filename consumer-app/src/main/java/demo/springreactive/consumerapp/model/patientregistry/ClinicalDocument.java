package demo.springreactive.consumerapp.model.patientregistry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalDocument {
	private String id;
	private String patientId;
	private String title;
	private String doc;
}
