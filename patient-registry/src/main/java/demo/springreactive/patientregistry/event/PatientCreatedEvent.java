package demo.springreactive.patientregistry.event;

import demo.springreactive.patientregistry.model.Patient;
import org.springframework.context.ApplicationEvent;

public class PatientCreatedEvent extends ApplicationEvent {
	public PatientCreatedEvent(Patient source) {
		super(source);
	}
}
