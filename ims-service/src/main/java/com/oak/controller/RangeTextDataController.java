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
import com.oak.dto.RangeTextDataDto;
import com.oak.entity.RangeTextDataEntity;
import com.oak.service.RangeTextDataService;

@RestController
@RequestMapping("/range-text-data")
public class RangeTextDataController extends AbstractSearchController<RangeTextDataDto, RangeTextDataEntity, Long> {
	private final RangeTextDataService service;

	@Autowired
	public RangeTextDataController(Logger logger, RangeTextDataService service) {
		super(service, logger);
		this.service = service;
	}

	@Secured("ROLE_DATA_CREATE")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public RangeTextDataDto create(@RequestBody RangeTextDataDto dto) {
		logger.info("Create RangeTextData: {}", dto);

		return service.create(dto);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = OK)
	public RangeTextDataDto read(@PathVariable long id) {
		logger.info("Read RangeTextData [ id: {} ]", id);

		return service.read(id);
	}

	@Secured("ROLE_DATA_UPDATE")
	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public RangeTextDataDto update(@PathVariable long id, @RequestBody RangeTextDataDto dto) {
		logger.info("Update RangeTextData: {}", dto);
		
		if (nonNull(dto)) {
			dto.setId(id);
		}

		return service.update(dto, id);
	}

	@Secured("ROLE_DATA_DELETE")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = ACCEPTED)
	public void delete(@PathVariable long id) {
		logger.info("Delete RangeTextData [ id: {} ]", id);

		service.delete(id);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_DATA_READ";
	}
}