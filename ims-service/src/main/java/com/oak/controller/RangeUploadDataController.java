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
import com.oak.dto.RangeUploadDataDto;
import com.oak.entity.RangeUploadDataEntity;
import com.oak.service.RangeUploadDataService;

@RestController
@RequestMapping("/range-upload-data")
public class RangeUploadDataController extends AbstractSearchController<RangeUploadDataDto, RangeUploadDataEntity, Long> {
	private final RangeUploadDataService service;

	@Autowired
	public RangeUploadDataController(Logger logger, RangeUploadDataService service) {
		super(service, logger);
		this.service = service;
	}

	@Secured("ROLE_DATA_CREATE")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public RangeUploadDataDto create(@RequestBody RangeUploadDataDto dto) {
		logger.info("Create RangeUploadData: {}", dto);

		return service.create(dto);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = OK)
	public RangeUploadDataDto read(@PathVariable long id) {
		logger.info("Read RangeUploadData [ id: {} ]", id);

		return service.read(id);
	}

	@Secured("ROLE_DATA_UPDATE")
	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public RangeUploadDataDto update(@PathVariable long id, @RequestBody RangeUploadDataDto dto) {
		logger.info("Update RangeUploadData: {}", dto);
		
		if (nonNull(dto)) {
			dto.setId(id);
		}

		return service.update(dto, id);
	}

	@Secured("ROLE_DATA_DELETE")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = ACCEPTED)
	public void delete(@PathVariable long id) {
		logger.info("Delete RangeUploadData [ id: {} ]", id);

		service.delete(id);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_DATA_READ";
	}
}