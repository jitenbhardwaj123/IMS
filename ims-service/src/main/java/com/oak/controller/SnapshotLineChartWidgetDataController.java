package com.oak.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oak.dto.FilterSelectionDto;
import com.oak.dto.LineChartData;
import com.oak.service.SnapshotLineChartWidgetDataService;

@RestController
@RequestMapping("/snapshot-line-chart-widget-data")
public class SnapshotLineChartWidgetDataController {
	private final Logger logger;
	private final SnapshotLineChartWidgetDataService service;

	@Autowired
	public SnapshotLineChartWidgetDataController(Logger logger, SnapshotLineChartWidgetDataService service) {
		this.logger = logger;
		this.service = service;
	}

	@Secured("ROLE_DATA_READ")
	@PostMapping(value = "/{widgetId}/{assetId}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = OK)
	public List<LineChartData> read(@PathVariable long widgetId, @PathVariable long assetId,
			@RequestBody FilterSelectionDto filter) {
		logger.info("Get Snapshot Line Chart Widget Data [ widgetId: {}, assetId: {}, filter: {} ]", widgetId, assetId, filter);
		
		return service.read(widgetId, assetId, filter);
	}
}
