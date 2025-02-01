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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oak.common.rest.controller.AbstractSearchController;
import com.oak.dto.PageDto;
import com.oak.entity.PageEntity;
import com.oak.service.PageService;

@RestController
@RequestMapping("/page")
public class PageController extends AbstractSearchController<PageDto, PageEntity, Long> {
	private final PageService service;

	@Autowired
	public PageController(Logger logger, PageService service) {
		super(service, logger);
		this.service = service;
	}

	@Secured("ROLE_CONFIG_CREATE")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public PageDto create(@RequestBody PageDto dto) {
		logger.info("Create Page: {}", dto);

		return service.create(dto);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = OK)
	public PageDto read(@PathVariable long id) {
		logger.info("Read Page [ id: {} ]", id);

		return service.read(id);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/root")
	@ResponseStatus(code = OK)
	public List<PageDto> readRootPages() {
		logger.info("Read root Pages");
		
		return service.readRootPages();
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/root/{assetTypeId}")
	@ResponseStatus(code = OK)
	public List<PageDto> readRootPages(@PathVariable long assetTypeId) {
		logger.info("Read root Pages for asset type [ id: {} ]", assetTypeId);
		
		return service.readRootPages(assetTypeId);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/dashboard/{assetTypeId}")
	@ResponseStatus(code = OK)
	public PageDto readDashboardPage(@PathVariable long assetTypeId) {
		logger.info("Read dashboard Page for asset type [ id: {} ]", assetTypeId);
		
		return service.readDashboardPage(assetTypeId);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/dashboard/root")
	@ResponseStatus(code = OK)
	public PageDto readRootDashboardPage() {
		logger.info("Read root dashboard page");
		
		// TODO: Assumption that there will be no more than two root assets in the system
		List<PageDto> pages = service.readRootDashboardPages();
		return pages.isEmpty() ? null : pages.get(0);
	}

	@Secured("ROLE_CONFIG_UPDATE")
	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public PageDto update(@PathVariable long id, @RequestBody PageDto dto) {
		logger.info("Update Page: {}", dto);
		
		if (nonNull(dto)) {
			dto.setId(id);
		}

		return service.update(dto, id);
	}

	@Secured("ROLE_CONFIG_DELETE")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = ACCEPTED)
	public void delete(@PathVariable long id) {
		logger.info("Delete Page [ id: {} ]", id);

		service.delete(id);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_DATA_READ,ROLE_CONFIG_READ";
	}
}