package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.AnalyticsEngineTemplateDto;
import com.oak.entity.AnalyticsEngineTemplateEntity;
import com.oak.mapper.AnalyticsEngineTemplateMapper;
import com.oak.repository.AnalyticsEngineTemplateRepository;
import com.oak.service.AnalyticsEngineTemplateExecutor;
import com.oak.service.AnalyticsEngineTemplateService;

@Service
@Transactional
public class AnalyticsEngineTemplateServiceImpl implements AnalyticsEngineTemplateService {
	private final Logger logger;
	private final AnalyticsEngineTemplateRepository repository;
	private final AnalyticsEngineTemplateMapper mapper;
	private final AnalyticsEngineTemplateExecutor executor;

	@Autowired
	public AnalyticsEngineTemplateServiceImpl(Logger logger, AnalyticsEngineTemplateRepository repository, AnalyticsEngineTemplateMapper mapper, AnalyticsEngineTemplateExecutor executor) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
		this.executor = executor;
	}

	@Override
	public AnalyticsEngineTemplateDto create(AnalyticsEngineTemplateDto dto) {
		executor.validateTemplate(dto);
		return commonCreate(dto);
	}

	@Override
	public AnalyticsEngineTemplateDto update(AnalyticsEngineTemplateDto dto, Long id) {
		executor.validateTemplate(dto);
		return commonUpdate(dto, id);
	}

	@Override
	public AnalyticsEngineTemplateDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<AnalyticsEngineTemplateDto, AnalyticsEngineTemplateEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<AnalyticsEngineTemplateEntity, Long> getRepository() {
		return repository;
	}
}
