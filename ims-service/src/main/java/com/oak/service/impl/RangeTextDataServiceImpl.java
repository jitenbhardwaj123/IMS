package com.oak.service.impl;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.dto.FilterSelectionDto;
import com.oak.dto.RangeTextDataDto;
import com.oak.entity.RangeTextDataEntity;
import com.oak.mapper.RangeTextDataMapper;
import com.oak.repository.RangeTextDataRepository;
import com.oak.service.RangeTextDataService;

@Service
@Transactional
public class RangeTextDataServiceImpl implements RangeTextDataService {
	private final Logger logger;
	private final JdbcTemplate jdbcTemplate;
	private final RangeTextDataRepository repository;
	private final RangeTextDataMapper mapper;
	
	@Value("${sql.in-clause.maxsize}")
	private int partitionSizeForInClause;
	
	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int jdbcBatchSize;

	@Autowired
	public RangeTextDataServiceImpl(Logger logger, JdbcTemplate jdbcTemplate, RangeTextDataRepository repository, RangeTextDataMapper mapper) {
		this.logger = logger;
		this.jdbcTemplate = jdbcTemplate;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public RangeTextDataDto create(RangeTextDataDto dto) {
		return commonCreate(dto);
	}

	@Override
	public void create(List<RangeTextDataDto> dtos) {
		repository.batchCreate(jdbcTemplate, dtos, jdbcBatchSize);
	}
	
	@Override
	public void update(List<RangeTextDataDto> dtos) {
		repository.batchUpdate(jdbcTemplate, dtos, jdbcBatchSize);
	}

	@Override
	public RangeTextDataDto update(RangeTextDataDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public RangeTextDataDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<RangeTextDataDto, RangeTextDataEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<RangeTextDataEntity, Long> getRepository() {
		return repository;
	}

	@Override
	public List<RangeTextDataEntity> getData(List<Long> elementIds, List<Long> assetIds) {
		return repository.findByElementIdInAndAssetIdIn(elementIds, assetIds);
	}

	@Override
	public List<RangeTextDataEntity> getData(FilterSelectionDto filter) {
		return repository.findByElementIdInAndAssetIdInAndEndDateAfterAndStartDateBefore(filter.getElementIds(),
				filter.getAssetIds(), filter.getRangeStart(), filter.getRangeEnd());
	}

	@Override
	public List<RangeTextDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds,
			long assetId, long sourceId, @IsNotNull("Start Date") Instant startDate, Instant endDate) {
		return repository.findByElementIdInAndAssetIdAndSourceIdAndStartDateAndEndDate(elementIds, assetId, sourceId, startDate, endDate);
	}

	@Override
	public List<RangeTextDataEntity> getData(long assetId, long sourceId, long elementId, List<Instant> startDates) {
		return ListUtils.partition(startDates, partitionSizeForInClause)
		 .stream()
		 .map(batchedDates -> repository.findByAssetIdAndSourceIdAndElementIdAndStartDateIn(assetId, sourceId, elementId, batchedDates))
		 .flatMap(Collection::stream)
		 .collect(toList());
	}

	@Override
	public List<RangeTextDataDto> findApplicableForDate(long elementId, long assetId, Instant checkDate) {
		return repository.findApplicableForDate(elementId, assetId, checkDate).stream().map(this::map).collect(toList());
	}
}
