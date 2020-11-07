package demo.springreactive.consumerapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import demo.springreactive.consumerapp.model.patientregistry.ClinicalDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalDocumentDTO {
	@JsonProperty("title")
	private String documentTitle;

	@JsonProperty("body")
	private String documentBody;

	public ClinicalDocumentDTO(ClinicalDocument doc) {
		this.documentTitle = doc.getTitle();
		this.documentBody = doc.getDoc();
	}
}
