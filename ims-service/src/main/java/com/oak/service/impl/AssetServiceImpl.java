package com.oak.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.AssetDto;
import com.oak.entity.AssetEntity;
import com.oak.mapper.AssetMapper;
import com.oak.repository.AssetRepository;
import com.oak.service.AssetService;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {
	private final Logger logger;
	private final AssetRepository repository;
	private final AssetMapper mapper;

	@Autowired
	public AssetServiceImpl(Logger logger, AssetRepository repository, AssetMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public List<AssetDto> readRootAssets() {
		logger.info("Get Root Assets");

		return repository.findByParentNull().stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public AssetDto create(AssetDto dto) {
		return commonCreate(dto);
	}

	@Override
	public AssetDto update(AssetDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public AssetDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public List<AssetDto> getAssetsByName(String name) {
		return repository.findByName(name).stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public List<AssetDto> getAssetsByIds(List<Long> assetIds) {
		return repository.findAllById(assetIds).stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public DtoAndEntityMapper<AssetDto, AssetEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<AssetEntity, Long> getRepository() {
		return repository;
	}
}
