package demo.springreactive.consumerapp.service;

import java.util.ArrayList;
import java.util.List;

import demo.springreactive.consumerapp.config.PatientRegistryConfiguration;
import demo.springreactive.consumerapp.model.PatientDTO;
import demo.springreactive.consumerapp.model.patientregistry.ClinicalDocument;
import demo.springreactive.consumerapp.model.patientregistry.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

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
			List<ClinicalDocument> documents = CollectionUtils.arrayToList(
					this.restTemplate.getForObject(
							this.registryConfig.getEndpoint().getAllPatientDocuments(),
							ClinicalDocument[].class,
							patient.getId())
			);
			patientDTOList.add(new PatientDTO(patient, documents));
		});

		return patientDTOList;
	}
}
