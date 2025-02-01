package com.oak.controller;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oak.common.rest.controller.AbstractSearchController;
import com.oak.dto.MeasurementLocationDto;
import com.oak.entity.MeasurementLocationEntity;
import com.oak.service.MeasurementLocationService;

@RestController
@RequestMapping("/measurement-location")
public class MeasurementLocationController extends AbstractSearchController<MeasurementLocationDto, MeasurementLocationEntity, Long> {
	private final MeasurementLocationService service;

	@Autowired
	public MeasurementLocationController(Logger logger, MeasurementLocationService service) {
		super(service, logger);
		this.service = service;
	}

	@Secured("ROLE_CONFIG_CREATE")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public MeasurementLocationDto create(@RequestBody MeasurementLocationDto dto) {
		logger.info("Create MeasurementLocation: {}", dto);

		return service.create(dto);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = OK)
	public MeasurementLocationDto read(@PathVariable long id) {
		logger.info("Read MeasurementLocation [ id: {} ]", id);

		return service.read(id);
	}

	@Secured("ROLE_CONFIG_UPDATE")
	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public MeasurementLocationDto update(@PathVariable long id, @RequestBody MeasurementLocationDto dto) {
		logger.info("Update MeasurementLocation: {}", dto);
		
		if (nonNull(dto)) {
			dto.setId(id);
		}

		return service.update(dto, id);
	}

	@Secured("ROLE_CONFIG_DELETE")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = ACCEPTED)
	public void delete(@PathVariable long id) {
		logger.info("Delete MeasurementLocation [ id: {} ]", id);

		service.delete(id);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_DATA_READ,ROLE_CONFIG_READ";
	}
}