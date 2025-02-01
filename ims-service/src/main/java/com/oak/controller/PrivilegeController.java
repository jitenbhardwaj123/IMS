package com.oak.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oak.common.rest.controller.AbstractSearchController;
import com.oak.dto.PrivilegeDto;
import com.oak.entity.PrivilegeEntity;
import com.oak.service.PrivilegeService;

@RestController
@RequestMapping("/privilege")
public class PrivilegeController extends AbstractSearchController<PrivilegeDto, PrivilegeEntity, Long> {

	@Autowired
	public PrivilegeController(Logger logger, PrivilegeService service) {
		super(service, logger);
	}

	@Override
	public String getSearchPrivilege() {
		return "ROLE_USER_READ";
	}
}
