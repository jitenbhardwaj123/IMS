package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.exception.model.EntityNotFoundException;
import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.RoleDto;
import com.oak.entity.RoleEntity;
import com.oak.mapper.RoleMapper;
import com.oak.repository.RoleRepository;
import com.oak.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	private final Logger logger;
	private final RoleRepository repository;
	private final RoleMapper mapper;

	@Autowired
	public RoleServiceImpl(Logger logger, RoleRepository repository, RoleMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public RoleDto create(RoleDto dto) {
		return commonCreate(dto);
	}

	@Override
	public RoleDto update(RoleDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public RoleDto read(Long id) {
		return commonRead(id);
	}
	
	@Override
	public RoleDto readByName(String roleName) {
		return mapper.toDto(repository.findByName(roleName).orElseThrow(() -> new EntityNotFoundException("No Role found with name: " + roleName)));
	}
	
	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<RoleDto, RoleEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<RoleEntity, Long> getRepository() {
		return repository;
	}
}
