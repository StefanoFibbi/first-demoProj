package demo.springreactive.consumerapp.unit;

import demo.springreactive.consumerapp.config.PatientRegistryConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.reactive.function.client.WebClient;

public class ReactivePatientConsumerServiceUT {

	private static int PATIENT_NUM = 5;
	private static int DOCS_PER_PATIENT = 5;


	@SpyBean
	private WebClient webClient;

	@Autowired
	private PatientRegistryConfiguration config;

//	private void setupWebClientMocks() {
//		RequestHeadersUriSpec specs = Mockito.mock(RequestHeadersUriSpec.class);
//		RequestHeadersSpec uriSpec = Mockito.mock(RequestHeadersSpec.class);
//		ResponseSpec responseSpec = Mockito.mock(ResponseSpec.class);
//
//		Mockito.when(this.webClient.get()).thenReturn(specs);
//		Mockito.when(specs.uri(anyString(), any(Class.class))).thenReturn(uriSpec);
//		Mockito.when(uriSpec.retrieve()).thenReturn(responseSpec);
//
//		Mockito.when(responseSpec.bodyToFlux(eq(Patient.class))).thenReturn(this.buildPatientsFlux(PATIENT_NUM));
//		Mockito.when(responseSpec.bodyToFlux(eq(ContactInfo.class))).thenReturn(this.buildPatientsFlux(PATIENT_NUM));
//		Mockito
//				.when(responseSpec.bodyToFlux(eq(ClinicalDocument.class)))
//				.thenReturn(this.buildPatientClinicalDocumentsFlux(PATIENT_NUM, DOCS_PER_PATIENT));
//	}
//
//	private Flux<Patient> buildPatientsFlux(int n) {
//		List<Patient> patients = new ArrayList<>();
//		for (int i = 0; i < n; i++) {
//			patients.add(new Patient("PAT_" + i, "FirstName_" + i, "LastName_" + i));
//		}
//
//		return Flux.fromIterable(patients);
//	}
//
//	private Flux<ClinicalDocument> buildPatientClinicalDocumentsFlux(int patientNum, int docsPerPatient) {
//		List<ClinicalDocument> docs = new ArrayList<>();
//		for (int patientId = 0; patientId < patientNum; patientId++) {
//			for (int docId = 0; docId < docsPerPatient; docId++) {
//				docs.add(new ClinicalDocument(
//						"DOC_" + docId,
//						"PAT_" + patientId,
//						"Title",
//						"Lorem ipsum"
//				));
//			}
//		}
//
//		return Flux.fromIterable(docs);
//	}

//	private Flux<ContactInfo> buildPatientContactInfoFlux(int patientNum) {
//		List<ContactInfo> patients = new ArrayList<>();
//		for (int i = 0; i < patientNum; i++) {
//			patients.add(new ContactInfo(
//					UUID.randomUUID().toString(),
//					"PAT_" + i + "@email.com",
//					"PAT_" + i,
//					"+39 000 000 0000",
//
//			));
//		}
//
//		return Flux.fromIterable(patients);
//	}


}
