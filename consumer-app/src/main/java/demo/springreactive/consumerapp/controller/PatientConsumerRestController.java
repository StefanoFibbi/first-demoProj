package demo.springreactive.consumerapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import demo.springreactive.consumerapp.model.PatientDTO;
import demo.springreactive.consumerapp.service.PatientConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/patients")
@Profile("classic")
public class PatientConsumerRestController {

	PatientConsumerService consumerService;

	@Autowired
	public PatientConsumerRestController(PatientConsumerService consumerService) {
		this.consumerService = consumerService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<PatientDTO> getAllPatients(HttpServletRequest request) {
		log.info("Request for GET {}", request.getRequestURI());
		return this.consumerService.getAllPatients();
	}

}
