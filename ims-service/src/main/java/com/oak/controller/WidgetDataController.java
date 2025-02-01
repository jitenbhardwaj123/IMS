package com.oak.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oak.dto.FilterSelectionDto;
import com.oak.dto.GenericDataDto;
import com.oak.dto.WidgetDataDto;
import com.oak.service.GenericDataService;
import com.oak.service.WidgetDataService;

@RestController
@RequestMapping("/widget-data")
public class WidgetDataController {
	private final Logger logger;
	private final WidgetDataService service;
	private final GenericDataService genericDataService;

	@Autowired
	public WidgetDataController(Logger logger, WidgetDataService service, GenericDataService genericDataService) {
		this.logger = logger;
		this.service = service;
		this.genericDataService = genericDataService;
	}

	@Secured("ROLE_DATA_READ")
	@PostMapping(value = "/{widgetId}/{assetId}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = OK)
	public List<WidgetDataDto> read(@PathVariable long widgetId, @PathVariable long assetId,
			@RequestBody FilterSelectionDto filter) {
		logger.info("Get Widget Data [ widgetId: {}, assetId: {}, filter: {} ]", widgetId, assetId, filter);
		
		return service.read(widgetId, assetId, filter);
	}

	@Secured("ROLE_DATA_READ")
	@GetMapping(value = "/{widgetId}/{assetId}/{sourceId}/{readingDate}")
	@ResponseStatus(code = OK)
	public List<GenericDataDto> readWidgetDataRow(@PathVariable long widgetId, @PathVariable long assetId,
			@PathVariable long sourceId, @PathVariable long readingDate) {
		logger.info("Get Widget Data [ widgetId: {}, assetId: {}, sourceId: {}, readingDate: {} ]", widgetId, assetId, sourceId, readingDate);

		return genericDataService.readWidgetDataRow(widgetId, assetId, sourceId, readingDate, null);
	}
	
	@Secured("ROLE_DATA_READ")
	@GetMapping(value = "/{widgetId}/{assetId}/{sourceId}/{readingDate}/{endDate}")
	@ResponseStatus(code = OK)
	public List<GenericDataDto> readWidgetDataRow(@PathVariable long widgetId, @PathVariable long assetId,
			@PathVariable long sourceId, @PathVariable long readingDate, @PathVariable long endDate) {
		logger.info("Get Widget Data [ widgetId: {}, assetId: {}, sourceId: {}, readingDate: {}, endDate: {} ]", widgetId, assetId, sourceId, readingDate, endDate);
		
		return genericDataService.readWidgetDataRow(widgetId, assetId, sourceId, readingDate, endDate);
	}

	@Secured("ROLE_DATA_CREATE")
	@PostMapping(value = "/create", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public void create(@RequestBody List<GenericDataDto> data) {
		logger.info("Create data: {}", data);

		genericDataService.create(data);
	}

	@Secured("ROLE_DATA_UPDATE")
	@PutMapping(value = "/update", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public void update(@RequestBody List<GenericDataDto> data) {
		logger.info("Update data: {}", data);
		
		genericDataService.save(data);
	}

	@Secured("ROLE_DATA_DELETE")
	@PostMapping(value = "/delete", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public void delete(@RequestBody List<GenericDataDto> data) {
		logger.info("Delete data [ id: {} ]", data);

		genericDataService.delete(data);
	}
}
