package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.AssetTypeDto;
import com.oak.entity.AssetTypeEntity;
import com.oak.mapper.AssetTypeMapper;
import com.oak.repository.AssetTypeRepository;
import com.oak.service.AssetTypeService;

@Service
@Transactional
public class AssetTypeServiceImpl implements AssetTypeService {
	private final Logger logger;
	private final AssetTypeRepository repository;
	private final AssetTypeMapper mapper;

	@Autowired
	public AssetTypeServiceImpl(Logger logger, AssetTypeRepository repository, AssetTypeMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public AssetTypeDto create(AssetTypeDto dto) {
		return commonCreate(dto);
	}

	@Override
	public AssetTypeDto update(AssetTypeDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public AssetTypeDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<AssetTypeDto, AssetTypeEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<AssetTypeEntity, Long> getRepository() {
		return repository;
	}
}
