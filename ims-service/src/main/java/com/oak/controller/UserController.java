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
import com.oak.dto.UserDto;
import com.oak.entity.UserEntity;
import com.oak.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractSearchController<UserDto, UserEntity, Long> {
	private final UserService service;

	@Autowired
	public UserController(Logger logger, UserService service) {
		super(service, logger);
		this.service = service;
	}

	@Secured("ROLE_USER_CREATE")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public UserDto create(@RequestBody UserDto dto) {
		logger.info("Create User: {}", dto);

		return service.create(dto);
	}
	
	@PostMapping(value = "/register", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = CREATED)
	public UserDto register(@RequestBody UserDto dto) {
		logger.info("Register User: {}", dto);
		
		return service.register(dto);
	}

	@PreAuthorize("hasAnyRole(#this.getThis().getSearchPrivilege())")
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = OK)
	public UserDto read(@PathVariable long id) {
		logger.info("Read User [ id: {} ]", id);

		return service.read(id);
	}

	@Secured("ROLE_USER_UPDATE")
	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = ACCEPTED)
	public UserDto update(@PathVariable long id, @RequestBody UserDto dto) {
		logger.info("Update User: {}", dto);
		
		if (nonNull(dto)) {
			dto.setId(id);
		}

		return service.update(dto, id);
	}

	@Secured("ROLE_USER_DELETE")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = ACCEPTED)
	public void delete(@PathVariable long id) {
		logger.info("Delete User [ id: {} ]", id);

		service.delete(id);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_USER_READ";
	}
}