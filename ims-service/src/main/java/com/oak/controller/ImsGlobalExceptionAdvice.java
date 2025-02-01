package com.oak.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.oak.common.exception.handler.AbstractGlobalExceptionAdvice;
import com.oak.common.exception.handler.ApiMessageGenerator;

@ControllerAdvice(basePackages = "com.oak.controller")
public class ImsGlobalExceptionAdvice extends AbstractGlobalExceptionAdvice {

	@Autowired
	public ImsGlobalExceptionAdvice(Logger logger, ApiMessageGenerator messageGenerator) {
		super(logger, messageGenerator);
	}
}
