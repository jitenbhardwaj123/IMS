package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.SourceDto;
import com.oak.entity.SourceEntity;
import com.oak.mapper.SourceMapper;
import com.oak.repository.SourceRepository;
import com.oak.service.SourceService;

@Service
@Transactional
public class SourceServiceImpl implements SourceService {
	private final Logger logger;
	private final SourceRepository repository;
	private final SourceMapper mapper;

	@Autowired
	public SourceServiceImpl(Logger logger, SourceRepository repository, SourceMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public SourceDto create(SourceDto dto) {
		return commonCreate(dto);
	}

	@Override
	public SourceDto update(SourceDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public SourceDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public SourceDto getAnalyticsSource() {	
		return repository.findByName("Analytics")
				.map(mapper::toDto)
				.orElseThrow(() -> new RuntimeException("Analytics Source is not configured!"));
	}

	@Override
	public DtoAndEntityMapper<SourceDto, SourceEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<SourceEntity, Long> getRepository() {
		return repository;
	}
}
