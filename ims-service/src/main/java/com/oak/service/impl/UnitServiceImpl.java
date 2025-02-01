package com.oak.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.UnitDto;
import com.oak.entity.UnitEntity;
import com.oak.mapper.UnitMapper;
import com.oak.repository.UnitRepository;
import com.oak.service.UnitService;

@Service
@Transactional
public class UnitServiceImpl implements UnitService {
	private final Logger logger;
	private final UnitRepository repository;
	private final UnitMapper mapper;

	@Autowired
	public UnitServiceImpl(Logger logger, UnitRepository repository, UnitMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public UnitDto create(UnitDto dto) {
		return commonCreate(dto);
	}

	@Override
	public UnitDto update(UnitDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public UnitDto read(Long id) {
		return commonRead(id);
	}
	
	@Override
	public UnitDto readByName(String unitName) {
		return mapper.toDto(repository.findByName(unitName).orElse(null));
	}
	
	@Override
	public 
	List<UnitDto> readByUnitType(long id) {
		return repository.findByUnitTypeId(id).stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<UnitDto, UnitEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<UnitEntity, Long> getRepository() {
		return repository;
	}
}
