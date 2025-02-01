package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.LimitTypeDto;
import com.oak.entity.LimitTypeEntity;
import com.oak.mapper.LimitTypeMapper;
import com.oak.repository.LimitTypeRepository;
import com.oak.service.LimitTypeService;

@Service
@Transactional
public class LimitTypeServiceImpl implements LimitTypeService {
	private final Logger logger;
	private final LimitTypeRepository repository;
	private final LimitTypeMapper mapper;

	@Autowired
	public LimitTypeServiceImpl(Logger logger, LimitTypeRepository repository, LimitTypeMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public LimitTypeDto create(LimitTypeDto dto) {
		return commonCreate(dto);
	}

	@Override
	public LimitTypeDto update(LimitTypeDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public LimitTypeDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<LimitTypeDto, LimitTypeEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<LimitTypeEntity, Long> getRepository() {
		return repository;
	}
}
