package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.exception.model.EntityNotFoundException;
import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.UserDto;
import com.oak.entity.UserEntity;
import com.oak.mapper.UserMapper;
import com.oak.repository.UserRepository;
import com.oak.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private final Logger logger;
	private final UserRepository repository;
	private final UserMapper mapper;

	@Autowired
	public UserServiceImpl(Logger logger, UserRepository repository, UserMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public UserDto create(UserDto dto) {
		return commonCreate(dto);
	}

	@Override
	public UserDto register(UserDto dto) {
		dto.setActive(false);
		dto.setRoles(null);
		return commonCreate(dto);
	}

	@Override
	public UserDto update(UserDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public UserDto read(Long id) {
		return commonRead(id);
	}
	
	@Override
	public UserDto readByUsername(String username) {
		return mapper.toDto(repository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("No User found with username: " + username)));
	}
	
	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<UserDto, UserEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<UserEntity, Long> getRepository() {
		return repository;
	}
}
