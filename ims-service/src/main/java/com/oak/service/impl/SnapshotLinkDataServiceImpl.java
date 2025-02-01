package com.oak.service.impl;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.dto.FilterSelectionDto;
import com.oak.dto.SnapshotLinkDataDto;
import com.oak.entity.SnapshotLinkDataEntity;
import com.oak.mapper.SnapshotLinkDataMapper;
import com.oak.repository.SnapshotLinkDataRepository;
import com.oak.service.SnapshotLinkDataService;

@Service
@Transactional
public class SnapshotLinkDataServiceImpl implements SnapshotLinkDataService {
	private final Logger logger;
	private final SnapshotLinkDataRepository repository;
	private final SnapshotLinkDataMapper mapper;

	@Autowired
	public SnapshotLinkDataServiceImpl(Logger logger, SnapshotLinkDataRepository repository, SnapshotLinkDataMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public SnapshotLinkDataDto create(SnapshotLinkDataDto dto) {
		return commonCreate(dto);
	}

	@Override
	public SnapshotLinkDataDto update(SnapshotLinkDataDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public SnapshotLinkDataDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<SnapshotLinkDataDto, SnapshotLinkDataEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<SnapshotLinkDataEntity, Long> getRepository() {
		return repository;
	}

	@Override
	public List<SnapshotLinkDataEntity> getData(List<Long> elementIds, List<Long> assetIds) {
		return repository.findByElementIdInAndAssetIdIn(elementIds, assetIds);
	}

	@Override
	public List<SnapshotLinkDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter) {
		return repository.findByElementIdInAndAssetIdInAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(filter.getElementIds(),
				filter.getAssetIds(), filter.getRangeStart(), filter.getRangeEnd());
	}

	@Override
	public List<SnapshotLinkDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds,
			long assetId, long sourceId, @IsNotNull("Reading Date") Instant readingDate) {
		return repository.findByElementIdInAndAssetIdAndSourceIdAndReadingDate(elementIds, assetId, sourceId, readingDate);
	}
}
