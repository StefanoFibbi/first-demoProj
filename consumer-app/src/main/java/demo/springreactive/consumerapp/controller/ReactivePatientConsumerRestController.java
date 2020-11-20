package demo.springreactive.consumerapp.controller;

import javax.servlet.http.HttpServletRequest;

import demo.springreactive.consumerapp.model.PatientDTO;
import demo.springreactive.consumerapp.service.ReactivePatientConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@Profile("reactive")
@RestController
@RequestMapping("/v1/patients")
public class ReactivePatientConsumerRestController {

	ReactivePatientConsumerService consumerService;

	@Autowired
	public ReactivePatientConsumerRestController(ReactivePatientConsumerService consumerService) {
		this.consumerService = consumerService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<PatientDTO> getAllPatients(HttpServletRequest request) {
		log.info("Request for GET {}", request.getRequestURI());
		return this.consumerService.getAllPatients();
	}

}
