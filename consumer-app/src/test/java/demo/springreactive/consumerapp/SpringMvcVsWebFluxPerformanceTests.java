package demo.springreactive.consumerapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import demo.springreactive.consumerapp.config.PatientRegistryConfiguration;
import demo.springreactive.consumerapp.model.patientregistry.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SpringBootTest
class SpringMvcVsWebFluxPerformanceTests {

	private static final int REQUEST_NUM = 4;

	@Autowired
	PatientRegistryConfiguration config;

	@Test
	void nCallsWithClassicRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(this.config.getHost()));

		for (int i = 0; i < REQUEST_NUM; i++) {
			Patient[] patients = restTemplate.getForObject(
					this.config.getEndpoint().getAllPatients(),
					Patient[].class
			);

			System.out.println(Arrays.toString(patients));
		}
	}

	@Test
	void nParallelCallsWithClassicRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(this.config.getHost()));

		List<Integer> callNums = new ArrayList<>();
		for (int i = 0; i < 100; i++) callNums.add(i);

		callNums
				.parallelStream()
				.forEach(i -> {
					Patient[] patients = restTemplate.getForObject(
							this.config.getEndpoint().getAllPatients(),
							Patient[].class
					);

					System.out.println(Arrays.toString(patients));
				});
	}

	@Test
	void nCallsWithWebClient_ForLoopAndBlockingApproach() {
		WebClient webClient = WebClient.create(this.config.getHost());

		for (int i = 0; i < REQUEST_NUM; i++) {
			List<Patient> patients = webClient
					.get()
					.uri(this.config.getEndpoint().getAllPatients())
					.retrieve()
					.bodyToFlux(Patient.class)
					.collectList()
					.block();

			System.out.println(patients);
		}
	}

	@Test
	void nCallsWithWebClient_FluxAndNoBlockingApproach() {
		WebClient webClient = WebClient.create(this.config.getHost());

		Flux
				.range(1, REQUEST_NUM)
				.flatMap(reqNum ->
						webClient
								.get()
								.uri(this.config.getEndpoint().getAllPatients())
								.retrieve()
								.bodyToFlux(Patient.class)
								.collectList()
								.doOnNext(System.out::println))
				.blockLast();
	}

	@Test
	void aThousandCallsWithWebClient_NonBlockingApproach() {
		WebClient webClient = WebClient.create(this.config.getHost());

		Flux
				.range(1, 1000)
				.flatMap(reqNum ->
						webClient
								.get()
								.uri(this.config.getEndpoint().getAllPatients())
								.retrieve()
								.bodyToFlux(Patient.class)
								.collectList())
				.doOnNext(System.out::println)
				.blockLast();
	}

	@Test
	void aThousandCallsWithWebClient_ParallelNonBlockingApproach() {
		WebClient webClient = WebClient.create(this.config.getHost());

		Flux
				.range(1, 1000)
				.parallel()
				.runOn(Schedulers.elastic())
				.flatMap(reqNum ->
						webClient
								.get()
								.uri(this.config.getEndpoint().getAllPatients())
								.retrieve()
								.bodyToFlux(Patient.class)
								.collectList())
				.doOnNext(System.out::println)
				.sequential()
				.blockLast();
	}

}
