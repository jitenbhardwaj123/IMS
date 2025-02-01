package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.exception.model.EntityNotFoundException;
import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.PrivilegeDto;
import com.oak.entity.PrivilegeEntity;
import com.oak.mapper.PrivilegeMapper;
import com.oak.repository.PrivilegeRepository;
import com.oak.service.PrivilegeService;

@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {
	private final Logger logger;
	private final PrivilegeRepository repository;
	private final PrivilegeMapper mapper;

	@Autowired
	public PrivilegeServiceImpl(Logger logger, PrivilegeRepository repository, PrivilegeMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public PrivilegeDto create(PrivilegeDto dto) {
		return commonCreate(dto);
	}

	@Override
	public PrivilegeDto update(PrivilegeDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public PrivilegeDto read(Long id) {
		return commonRead(id);
	}
	
	@Override
	public PrivilegeDto readByName(String privilegeName) {
		return mapper.toDto(repository.findByName(privilegeName).orElseThrow(() -> new EntityNotFoundException("No Privilege found with name: " + privilegeName)));
	}
	
	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<PrivilegeDto, PrivilegeEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<PrivilegeEntity, Long> getRepository() {
		return repository;
	}
}
