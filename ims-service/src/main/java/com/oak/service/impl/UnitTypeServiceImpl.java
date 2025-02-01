package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.UnitTypeDto;
import com.oak.entity.UnitTypeEntity;
import com.oak.mapper.UnitTypeMapper;
import com.oak.repository.UnitTypeRepository;
import com.oak.service.UnitTypeService;

@Service
@Transactional
public class UnitTypeServiceImpl implements UnitTypeService {
	private final Logger logger;
	private final UnitTypeRepository repository;
	private final UnitTypeMapper mapper;

	@Autowired
	public UnitTypeServiceImpl(Logger logger, UnitTypeRepository repository, UnitTypeMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public UnitTypeDto create(UnitTypeDto dto) {
		return commonCreate(dto);
	}

	@Override
	public UnitTypeDto update(UnitTypeDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public UnitTypeDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<UnitTypeDto, UnitTypeEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<UnitTypeEntity, Long> getRepository() {
		return repository;
	}
}
