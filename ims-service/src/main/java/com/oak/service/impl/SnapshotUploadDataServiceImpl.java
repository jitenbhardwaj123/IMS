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
import com.oak.dto.SnapshotUploadDataDto;
import com.oak.entity.SnapshotUploadDataEntity;
import com.oak.mapper.SnapshotUploadDataMapper;
import com.oak.repository.SnapshotUploadDataRepository;
import com.oak.service.SnapshotUploadDataService;

@Service
@Transactional
public class SnapshotUploadDataServiceImpl implements SnapshotUploadDataService {
	private final Logger logger;
	private final SnapshotUploadDataRepository repository;
	private final SnapshotUploadDataMapper mapper;

	@Autowired
	public SnapshotUploadDataServiceImpl(Logger logger, SnapshotUploadDataRepository repository, SnapshotUploadDataMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public SnapshotUploadDataDto create(SnapshotUploadDataDto dto) {
		return commonCreate(dto);
	}

	@Override
	public SnapshotUploadDataDto update(SnapshotUploadDataDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public SnapshotUploadDataDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<SnapshotUploadDataDto, SnapshotUploadDataEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<SnapshotUploadDataEntity, Long> getRepository() {
		return repository;
	}

	@Override
	public List<SnapshotUploadDataEntity> getData(List<Long> elementIds, List<Long> assetIds) {
		return repository.findByElementIdInAndAssetIdIn(elementIds, assetIds);
	}

	@Override
	public List<SnapshotUploadDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter) {
		return repository.findByElementIdInAndAssetIdInAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(filter.getElementIds(),
				filter.getAssetIds(), filter.getRangeStart(), filter.getRangeEnd());
	}

	@Override
	public List<SnapshotUploadDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds,
			long assetId, long sourceId, @IsNotNull("Reading Date") Instant readingDate) {
		return repository.findByElementIdInAndAssetIdAndSourceIdAndReadingDate(elementIds, assetId, sourceId, readingDate);
	}
}
