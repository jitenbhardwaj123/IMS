package com.oak.controller;

import static com.oak.service.impl.FileResourceUtils.fromMultiPartFile;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.ALL;
import static org.springframework.http.MediaType.parseMediaType;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.oak.entity.FileResourceEntity;
import com.oak.service.FileResourceService;

@RestController
@RequestMapping("/file-resource")
public class FileResourceController extends AbstractSearchController<FileResourceDto, FileResourceEntity, Long> {
	private final FileResourceService service;

	@Autowired
	public FileResourceController(Logger logger, FileResourceService service) {
		super(service, logger);
		this.service = service;
	}

	@Secured("ROLE_DATA_CREATE")
	@PostMapping
    @ResponseStatus(code = CREATED)
	public FileResourceDto create(@RequestBody @RequestParam("file") MultipartFile multipartFile) {
		FileResourceDto fileResource = fromMultiPartFile(multipartFile);
		logger.info("Create FileResource [ BODY: {} ] ", fileResource);

		return service.create(fileResource);
	}

	@Secured("ROLE_DATA_CREATE")
	@PostMapping("/raw-files")
    @ResponseStatus(code = CREATED)
	public List<FileResourceDto> create(@RequestBody @RequestParam("files") List<MultipartFile> multipartFiles) {
		logger.info("Create FileResources [ Files: {} ] ", multipartFiles);

		return service.createFromFiles(multipartFiles);
	}
	
	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = OK)
	public FileResourceDto read(@PathVariable long id) {
		logger.info("Read FileResource [ id: {} ]", id);

		return service.read(id);
	}

	@GetMapping(value = "/content/{id}")
	@ResponseStatus(code = OK)
	public ResponseEntity<byte[]> serveFileResource(@PathVariable long id) {
		logger.info("Get File Content for FileResource [ id: {} ]", id);

        FileResourceDto fileResource = service.read(id);

        return ResponseEntity
                .ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFileNameWithExtension() + "\"")
                .contentLength(fileResource.getFileSize())
                .contentType(isNotBlank(fileResource.getHttpContentType()) ? parseMediaType(fileResource.getHttpContentType()) : ALL)
                .body(fileResource.getRawData());
    }

	@Secured("ROLE_DATA_UPDATE")
	@PutMapping(value = "/{id}")
	public FileResourceDto update(@PathVariable long id, @RequestParam("file") MultipartFile multipartFile) {
        FileResourceDto fileResource = fromMultiPartFile(multipartFile);
        fileResource.setId(id);
        logger.info("Update FileResource [ Id: {}, BODY: {} ] ", id, fileResource);

        return service.update(fileResource, id);
    }

	@Secured("ROLE_DATA_DELETE")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = ACCEPTED)
	public void delete(@PathVariable long id) {
		logger.info("Delete FileResource [ id: {} ]", id);

		service.delete(id);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_DATA_READ";
	}
}