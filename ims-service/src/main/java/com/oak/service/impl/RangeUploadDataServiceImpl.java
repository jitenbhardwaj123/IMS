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
import com.oak.dto.RangeUploadDataDto;
import com.oak.entity.RangeUploadDataEntity;
import com.oak.mapper.RangeUploadDataMapper;
import com.oak.repository.RangeUploadDataRepository;
import com.oak.service.RangeUploadDataService;

@Service
@Transactional
public class RangeUploadDataServiceImpl implements RangeUploadDataService {
	private final Logger logger;
	private final RangeUploadDataRepository repository;
	private final RangeUploadDataMapper mapper;

	@Autowired
	public RangeUploadDataServiceImpl(Logger logger, RangeUploadDataRepository repository, RangeUploadDataMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public RangeUploadDataDto create(RangeUploadDataDto dto) {
		return commonCreate(dto);
	}

	@Override
	public RangeUploadDataDto update(RangeUploadDataDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public RangeUploadDataDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<RangeUploadDataDto, RangeUploadDataEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<RangeUploadDataEntity, Long> getRepository() {
		return repository;
	}

	@Override
	public List<RangeUploadDataEntity> getData(List<Long> elementIds, List<Long> assetIds) {
		return repository.findByElementIdInAndAssetIdIn(elementIds, assetIds);
	}

	@Override
	public List<RangeUploadDataEntity> getData(FilterSelectionDto filter) {
		return repository.findByElementIdInAndAssetIdInAndEndDateAfterAndStartDateBefore(filter.getElementIds(),
				filter.getAssetIds(), filter.getRangeStart(), filter.getRangeEnd());
	}

	@Override
	public List<RangeUploadDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds,
			long assetId, long sourceId, @IsNotNull("Start Date") Instant startDate, Instant endDate) {
		return repository.findByElementIdInAndAssetIdAndSourceIdAndStartDateAndEndDate(elementIds, assetId, sourceId, startDate, endDate);
	}
}
