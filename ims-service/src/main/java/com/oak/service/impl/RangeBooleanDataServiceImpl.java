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
import com.oak.dto.RangeBooleanDataDto;
import com.oak.entity.RangeBooleanDataEntity;
import com.oak.mapper.RangeBooleanDataMapper;
import com.oak.repository.RangeBooleanDataRepository;
import com.oak.service.RangeBooleanDataService;

@Service
@Transactional
public class RangeBooleanDataServiceImpl implements RangeBooleanDataService {
	private final Logger logger;
	private final JdbcTemplate jdbcTemplate;
	private final RangeBooleanDataRepository repository;
	private final RangeBooleanDataMapper mapper;
	
	@Value("${sql.in-clause.maxsize}")
	private int partitionSizeForInClause;
	
	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int jdbcBatchSize;

	@Autowired
	public RangeBooleanDataServiceImpl(Logger logger, JdbcTemplate jdbcTemplate, RangeBooleanDataRepository repository, RangeBooleanDataMapper mapper) {
		this.logger = logger;
		this.jdbcTemplate = jdbcTemplate;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public RangeBooleanDataDto create(RangeBooleanDataDto dto) {
		return commonCreate(dto);
	}

	@Override
	public void create(List<RangeBooleanDataDto> dtos) {
		repository.batchCreate(jdbcTemplate, dtos, jdbcBatchSize);
	}
	
	@Override
	public void update(List<RangeBooleanDataDto> dtos) {
		repository.batchUpdate(jdbcTemplate, dtos, jdbcBatchSize);
	}

	@Override
	public RangeBooleanDataDto update(RangeBooleanDataDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public RangeBooleanDataDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<RangeBooleanDataDto, RangeBooleanDataEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<RangeBooleanDataEntity, Long> getRepository() {
		return repository;
	}

	@Override
	public List<RangeBooleanDataEntity> getData(List<Long> elementIds, List<Long> assetIds) {
		return repository.findByElementIdInAndAssetIdIn(elementIds, assetIds);
	}

	@Override
	public List<RangeBooleanDataEntity> getData(FilterSelectionDto filter) {
		return repository.findByElementIdInAndAssetIdInAndEndDateAfterAndStartDateBefore(filter.getElementIds(),
				filter.getAssetIds(), filter.getRangeStart(), filter.getRangeEnd());
	}

	@Override
	public List<RangeBooleanDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds,
			long assetId, long sourceId, @IsNotNull("Start Date") Instant startDate, Instant endDate) {
		return repository.findByElementIdInAndAssetIdAndSourceIdAndStartDateAndEndDate(elementIds, assetId, sourceId, startDate, endDate);
	}

	@Override
	public List<RangeBooleanDataEntity> getData(long assetId, long sourceId, long elementId, List<Instant> startDates) {
		return ListUtils.partition(startDates, partitionSizeForInClause)
		 .stream()
		 .map(batchedDates -> repository.findByAssetIdAndSourceIdAndElementIdAndStartDateIn(assetId, sourceId, elementId, batchedDates))
		 .flatMap(Collection::stream)
		 .collect(toList());
	}

	@Override
	public List<RangeBooleanDataDto> findApplicableForDate(long elementId, long assetId, Instant checkDate) {
		return repository.findApplicableForDate(elementId, assetId, checkDate).stream().map(this::map).collect(toList());
	}
}
