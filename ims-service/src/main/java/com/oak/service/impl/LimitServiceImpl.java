package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.LimitDto;
import com.oak.entity.LimitEntity;
import com.oak.mapper.LimitMapper;
import com.oak.repository.LimitRepository;
import com.oak.service.LimitService;

@Service
@Transactional
public class LimitServiceImpl implements LimitService {
	private final Logger logger;
	private final LimitRepository repository;
	private final LimitMapper mapper;

	@Autowired
	public LimitServiceImpl(Logger logger, LimitRepository repository, LimitMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public LimitDto create(LimitDto dto) {
		return commonCreate(dto);
	}

	@Override
	public LimitDto update(LimitDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public LimitDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<LimitDto, LimitEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<LimitEntity, Long> getRepository() {
		return repository;
	}
}
