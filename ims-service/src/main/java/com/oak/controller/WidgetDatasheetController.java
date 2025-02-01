package com.oak.controller;

import static com.oak.service.impl.FileResourceUtils.fromMultiPartFile;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oak.service.DataImportProcessor;

@RestController
@RequestMapping("/widget-datasheet")
public class WidgetDatasheetController {
	private final Logger logger;
	private final DataImportProcessor service;

	@Autowired
	public WidgetDatasheetController(Logger logger, DataImportProcessor service) {
		this.logger = logger;
		this.service = service;
	}

	@GetMapping(value = "/template/{widgetId}")
	@ResponseStatus(code = OK)
	public ResponseEntity<byte[]> getTemplate(@PathVariable long widgetId) {
		logger.info("Get template for Widget [ id: {} ]", widgetId);

		Pair<String, byte[]> template = service.getTemplate(widgetId);

        return ResponseEntity
                .ok()
                .header("Content-Disposition", format("attachment; filename=\"%s.xlsx\"", template.getKey()))
                .contentLength(template.getValue().length)
                .contentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(template.getValue());
	}

	@Secured("ROLE_DATA_CREATE")
	@PostMapping(value = "/import/{widgetId}")
	@ResponseStatus(code = ACCEPTED)
	public void importWidgetData(
			@PathVariable long widgetId, 
			@RequestHeader("X-Timezone") String timezone,
			@RequestBody @RequestParam("file") MultipartFile multipartFile) {
		logger.info("Import file [ name: {} ] for Widget [ id: {} ] ", multipartFile.getName(), widgetId);
		logger.info("Detected Client Timezone[ name: {} ]", timezone);

		service.importWidgetData(widgetId, timezone, fromMultiPartFile(multipartFile));
	}
}
