package demo.springreactive.consumerapp.model.patientregistry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	private String id;
	private String fullName;
	private String email;
}
