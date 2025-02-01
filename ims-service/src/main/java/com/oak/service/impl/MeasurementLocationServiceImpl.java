package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.MeasurementLocationDto;
import com.oak.entity.MeasurementLocationEntity;
import com.oak.mapper.MeasurementLocationMapper;
import com.oak.repository.MeasurementLocationRepository;
import com.oak.service.MeasurementLocationService;

@Service
@Transactional
public class MeasurementLocationServiceImpl implements MeasurementLocationService {
	private final Logger logger;
	private final MeasurementLocationRepository repository;
	private final MeasurementLocationMapper mapper;

	@Autowired
	public MeasurementLocationServiceImpl(Logger logger, MeasurementLocationRepository repository, MeasurementLocationMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public MeasurementLocationDto create(MeasurementLocationDto dto) {
		return commonCreate(dto);
	}

	@Override
	public MeasurementLocationDto update(MeasurementLocationDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public MeasurementLocationDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<MeasurementLocationDto, MeasurementLocationEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<MeasurementLocationEntity, Long> getRepository() {
		return repository;
	}
}
