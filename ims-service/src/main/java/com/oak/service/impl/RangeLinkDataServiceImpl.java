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
import com.oak.dto.RangeLinkDataDto;
import com.oak.entity.RangeLinkDataEntity;
import com.oak.mapper.RangeLinkDataMapper;
import com.oak.repository.RangeLinkDataRepository;
import com.oak.service.RangeLinkDataService;

@Service
@Transactional
public class RangeLinkDataServiceImpl implements RangeLinkDataService {
	private final Logger logger;
	private final RangeLinkDataRepository repository;
	private final RangeLinkDataMapper mapper;

	@Autowired
	public RangeLinkDataServiceImpl(Logger logger, RangeLinkDataRepository repository, RangeLinkDataMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public RangeLinkDataDto create(RangeLinkDataDto dto) {
		return commonCreate(dto);
	}

	@Override
	public RangeLinkDataDto update(RangeLinkDataDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public RangeLinkDataDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<RangeLinkDataDto, RangeLinkDataEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<RangeLinkDataEntity, Long> getRepository() {
		return repository;
	}

	@Override
	public List<RangeLinkDataEntity> getData(List<Long> elementIds, List<Long> assetIds) {
		return repository.findByElementIdInAndAssetIdIn(elementIds, assetIds);
	}

	@Override
	public List<RangeLinkDataEntity> getData(FilterSelectionDto filter) {
		return repository.findByElementIdInAndAssetIdInAndEndDateAfterAndStartDateBefore(filter.getElementIds(),
				filter.getAssetIds(), filter.getRangeStart(), filter.getRangeEnd());
	}

	@Override
	public List<RangeLinkDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds,
			long assetId, long sourceId, @IsNotNull("Start Date") Instant startDate, Instant endDate) {
		return repository.findByElementIdInAndAssetIdAndSourceIdAndStartDateAndEndDate(elementIds, assetId, sourceId, startDate, endDate);
	}
}
