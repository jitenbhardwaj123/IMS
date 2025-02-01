package com.oak.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oak.common.rest.controller.AbstractSearchController;
import com.oak.dto.FileResourceDto;
import com.oak.dto.UploadRecordDto;
import com.oak.entity.UploadRecordEntity;
import com.oak.service.UploadRecordService;

@RestController
@RequestMapping("/upload-record")
public class UploadRecordController extends AbstractSearchController<UploadRecordDto, UploadRecordEntity, Long> {
	private final UploadRecordService service;

	@Autowired
	public UploadRecordController(Logger logger, UploadRecordService service) {
		super(service, logger);
		this.service = service;
	}

	@Secured("ROLE_DATA_CREATE")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public UploadRecordDto create(@RequestBody UploadRecordDto dto) {
		logger.info("Create UploadRecord: {}", dto);

		return service.create(dto);
	}

	@Secured("ROLE_DATA_CREATE")
	@PostMapping("/file-resources")
    @ResponseStatus(code = CREATED)
	public UploadRecordDto create(@RequestBody @RequestParam("files") List<MultipartFile> multipartFiles) {
		logger.info("Create UploadRecord [ Files: {} ] ", multipartFiles);

		return service.create(multipartFiles);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = OK)
	public UploadRecordDto read(@PathVariable long id) {
		logger.info("Read UploadRecord [ id: {} ]", id);

		return service.read(id);
	}

	// TODO: Move this to an aggregation service
	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/file-resources/{id}")
	@ResponseStatus(code = OK)
	public List<FileResourceDto> getFileResources(@PathVariable long id) {
		logger.info("getFileResources for UploadRecord [ id: {} ]", id);

		return service.getFileResources(id);
	}

	@Secured("ROLE_DATA_UPDATE")
	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public UploadRecordDto update(@PathVariable long id, @RequestBody UploadRecordDto dto) {
		logger.info("Update UploadRecord: {}", dto);
		
		if (nonNull(dto)) {
			dto.setId(id);
		}

		return service.update(dto, id);
	}

	@Secured("ROLE_DATA_DELETE")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = ACCEPTED)
	public void delete(@PathVariable long id) {
		logger.info("Delete UploadRecord [ id: {} ]", id);

		service.delete(id);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_DATA_READ";
	}
}