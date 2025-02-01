package com.oak.controller;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;

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
import com.oak.dto.WidgetDto;
import com.oak.entity.WidgetEntity;
import com.oak.service.WidgetService;

@RestController
@RequestMapping("/widget")
public class WidgetController extends AbstractSearchController<WidgetDto, WidgetEntity, Long> {
	private final WidgetService service;

	@Autowired
	public WidgetController(Logger logger, WidgetService service) {
		super(service, logger);
		this.service = service;
	}

	@Secured("ROLE_CONFIG_CREATE")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public WidgetDto create(@RequestBody WidgetDto dto) {
		logger.info("Create Widget: {}", dto);

		return service.create(dto);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = OK)
	public WidgetDto read(@PathVariable long id) {
		logger.info("Read Widget [ id: {} ]", id);

		return service.read(id);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/asset-type/{assetTypeId}")
	@ResponseStatus(code = OK)
	public List<WidgetDto> getWidgetsForAssetType(@PathVariable long assetTypeId) {
		logger.info("Get Widgets for AssetType [ id: {} ]", assetTypeId);
		return service.getWidgetsForAssetTypes(asList(assetTypeId));
	}

	@Secured("ROLE_CONFIG_UPDATE")
	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public WidgetDto update(@PathVariable long id, @RequestBody WidgetDto dto) {
		logger.info("Update Widget: {}", dto);
		
		if (nonNull(dto)) {
			dto.setId(id);
		}

		return service.update(dto, id);
	}

	@Secured("ROLE_CONFIG_DELETE")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = ACCEPTED)
	public void delete(@PathVariable long id) {
		logger.info("Delete Widget [ id: {} ]", id);

		service.delete(id);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_DATA_READ,ROLE_CONFIG_READ";
	}
}