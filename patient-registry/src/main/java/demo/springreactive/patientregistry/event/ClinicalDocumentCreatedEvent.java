package demo.springreactive.patientregistry.event;

import demo.springreactive.patientregistry.model.ClinicalDocument;
import org.springframework.context.ApplicationEvent;

public class ClinicalDocumentCreatedEvent extends ApplicationEvent {
	public ClinicalDocumentCreatedEvent(ClinicalDocument source) {
		super(source);
	}
}
