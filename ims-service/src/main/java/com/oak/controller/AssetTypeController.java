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
import com.oak.dto.AssetTypeDto;
import com.oak.entity.AssetTypeEntity;
import com.oak.service.AssetTypeService;

@RestController
@RequestMapping("/asset-type")
public class AssetTypeController extends AbstractSearchController<AssetTypeDto, AssetTypeEntity, Long> {
	private final AssetTypeService service;

	@Autowired
	public AssetTypeController(Logger logger, AssetTypeService service) {
		super(service, logger);
		this.service = service;
	}

	@Secured("ROLE_ASSET_CREATE")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public AssetTypeDto create(@RequestBody AssetTypeDto dto) {
		logger.info("Create AssetType: {}", dto);

		return service.create(dto);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = OK)
	public AssetTypeDto read(@PathVariable long id) {
		logger.info("Read AssetType [ id: {} ]", id);

		return service.read(id);
	}

	@Secured("ROLE_ASSET_UPDATE")
	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public AssetTypeDto update(@PathVariable long id, @RequestBody AssetTypeDto dto) {
		logger.info("Update AssetType: {}", dto);
		
		if (nonNull(dto)) {
			dto.setId(id);
		}

		return service.update(dto, id);
	}

	@Secured("ROLE_ASSET_DELETE")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = ACCEPTED)
	public void delete(@PathVariable long id) {
		logger.info("Delete AssetType [ id: {} ]", id);

		service.delete(id);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_DATA_READ,ROLE_ASSET_READ";
	}
}