package demo.springreactive.consumerapp.service;

import java.util.ArrayList;
import java.util.List;

import demo.springreactive.consumerapp.config.PatientRegistryConfiguration;
import demo.springreactive.consumerapp.model.PatientDTO;
import demo.springreactive.consumerapp.model.patientregistry.ClinicalDocument;
import demo.springreactive.consumerapp.model.patientregistry.ContactInfo;
import demo.springreactive.consumerapp.model.patientregistry.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Slf4j
@Service
public class PatientConsumerService {

	private final RestTemplate restTemplate;
	private final PatientRegistryConfiguration registryConfig;

	@Autowired
	public PatientConsumerService(PatientRegistryConfiguration registryConfig) {
		this.registryConfig = registryConfig;

		this.restTemplate = new RestTemplate();
		this.restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(registryConfig.getHost()));
	}

	public List<PatientDTO> getAllPatients() {
		List<PatientDTO> patientDTOList = new ArrayList<>();
		List<Patient> patientList = CollectionUtils.arrayToList(
				this.restTemplate.getForObject(this.registryConfig.getEndpoint().getAllPatients(), Patient[].class));

		patientList.forEach(patient -> {
			log.info("Request documents for patient {}", patient.getId());
			List<ClinicalDocument> documents = CollectionUtils.arrayToList(
					this.restTemplate.getForObject(
							this.registryConfig.getEndpoint().getAllPatientDocuments(),
							ClinicalDocument[].class,
							patient.getId())
			);
			log.info("Documents of patient {}: {}", patient.getId(), documents);

			log.info("Request contacts for patient {}", patient.getId());
			ContactInfo contacts = this.restTemplate.getForObject(
					this.registryConfig.getEndpoint().getAllPatientContacts(),
					ContactInfo.class,
					patient.getId()
			);
			log.info("Contacts of patient {}: {}", patient.getId(), contacts);

			patientDTOList.add(new PatientDTO(patient, documents, contacts));
		});

		return patientDTOList;
	}
}
