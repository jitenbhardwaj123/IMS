package com.oak.service.impl;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.exception.model.EntityNotFoundException;
import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.PageDto;
import com.oak.entity.PageEntity;
import com.oak.mapper.PageMapper;
import com.oak.repository.PageRepository;
import com.oak.service.PageService;

@Service
@Transactional
public class PageServiceImpl implements PageService {
	private final Logger logger;
	private final PageRepository repository;
	private final PageMapper mapper;

	@Autowired
	public PageServiceImpl(Logger logger, PageRepository repository, PageMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public List<PageDto> readRootPages() {
		logger.info("Get Root Pages");

		return repository.findByParentNull().stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public List<PageDto> readRootPages(long assetTypeId) {
		logger.info("Read root Pages for asset type [ id: {} ]", assetTypeId);
		
		return repository.findByParentNullAndAssetTypesIdIn(asList(assetTypeId)).stream().map(mapper::toDto).collect(toList());
	}
	
	@Override
	public PageDto readDashboardPage(long assetTypeId) {
		logger.info("Read dashboard Page for asset type [ id: {} ]", assetTypeId);

		return mapper.toDto(repository.findByDashboardTrueAndAssetTypesId(assetTypeId)
				.orElseThrow(() -> new EntityNotFoundException("No Dashboard found for the Asset Type")));
	}

	@Override
	public List<PageDto> readRootDashboardPages() {
		logger.info("Read all root dashboard pages");
		
		return repository.findByParentNullAndDashboardTrue().stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public PageDto create(PageDto dto) {
		return commonCreate(dto);
	}

	@Override
	public PageDto update(PageDto dto, Long id) {
		getLogger().info("Update Page {}", dto);
		
		PageEntity existingEntity = getRepository().findById(id).get();
		existingEntity.setWidgets(null);
		existingEntity = getRepository().saveAndFlush(existingEntity);

		PageEntity updatedEntity = getMapper().toEntity(dto);
		updatedEntity = existingEntity.merge(updatedEntity);
		updatedEntity = getRepository().saveAndFlush(updatedEntity);
		getRepository().refresh(updatedEntity);
		return getMapper().toDto(updatedEntity);
	}

	@Override
	public PageDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public List<PageDto> getPagesByName(String name) {
		return repository.findByName(name).stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public DtoAndEntityMapper<PageDto, PageEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<PageEntity, Long> getRepository() {
		return repository;
	}
}
