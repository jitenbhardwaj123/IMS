package com.oak.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.WidgetDto;
import com.oak.entity.WidgetEntity;
import com.oak.mapper.WidgetMapper;
import com.oak.repository.WidgetRepository;
import com.oak.service.WidgetService;

@Service
@Transactional
public class WidgetServiceImpl implements WidgetService {
	private final Logger logger;
	private final WidgetRepository repository;
	private final WidgetMapper mapper;

	@Autowired
	public WidgetServiceImpl(Logger logger, WidgetRepository repository, WidgetMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public WidgetDto create(WidgetDto dto) {
		return commonCreate(dto);
	}

	@Override
	public WidgetDto update(WidgetDto dto, Long id) {
		getLogger().info("Update Widget {}", dto);
		
		WidgetEntity existingEntity = getRepository().findById(id).get();
		existingEntity.setElements(null);
		existingEntity.setSortOptions(null);
		existingEntity = getRepository().saveAndFlush(existingEntity);

		WidgetEntity updatedEntity = getMapper().toEntity(dto);
		updatedEntity = existingEntity.merge(updatedEntity);
		updatedEntity = getRepository().saveAndFlush(updatedEntity);
		getRepository().refresh(updatedEntity);
		return getMapper().toDto(updatedEntity);
	}

	@Override
	public WidgetDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public List<WidgetDto> getWidgetsForAssetTypes(List<Long> assetTypeIds) {
		return repository.findByAssetTypesIdIn(assetTypeIds).stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<WidgetDto, WidgetEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<WidgetEntity, Long> getRepository() {
		return repository;
	}
}
