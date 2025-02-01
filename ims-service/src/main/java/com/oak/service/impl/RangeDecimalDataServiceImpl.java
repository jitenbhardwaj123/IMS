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
import com.oak.dto.FilterSelectionDto;
import com.oak.dto.RangeDecimalDataDto;
import com.oak.entity.RangeDecimalDataEntity;
import com.oak.mapper.RangeDecimalDataMapper;
import com.oak.repository.RangeDecimalDataRepository;
import com.oak.service.RangeDecimalDataService;

@Service
@Transactional
public class RangeDecimalDataServiceImpl implements RangeDecimalDataService {
	private final Logger logger;
	private final JdbcTemplate jdbcTemplate;
	private final RangeDecimalDataRepository repository;
	private final RangeDecimalDataMapper mapper;
	
	@Value("${sql.in-clause.maxsize}")
	private int partitionSizeForInClause;
	
	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int jdbcBatchSize;

	@Autowired
	public RangeDecimalDataServiceImpl(Logger logger, JdbcTemplate jdbcTemplate, RangeDecimalDataRepository repository, RangeDecimalDataMapper mapper) {
		this.logger = logger;
		this.jdbcTemplate = jdbcTemplate;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public RangeDecimalDataDto create(RangeDecimalDataDto dto) {
		return commonCreate(dto);
	}

	@Override
	public void create(List<RangeDecimalDataDto> dtos) {
		repository.batchCreate(jdbcTemplate, dtos, jdbcBatchSize);
	}
	
	@Override
	public void update(List<RangeDecimalDataDto> dtos) {
		repository.batchUpdate(jdbcTemplate, dtos, jdbcBatchSize);
	}

	@Override
	public RangeDecimalDataDto update(RangeDecimalDataDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public RangeDecimalDataDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<RangeDecimalDataDto, RangeDecimalDataEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<RangeDecimalDataEntity, Long> getRepository() {
		return repository;
	}

	@Override
	public List<RangeDecimalDataEntity> getData(List<Long> elementIds, List<Long> assetIds) {
		return repository.findByElementIdInAndAssetIdIn(elementIds, assetIds);
	}

	@Override
	public List<RangeDecimalDataEntity> getData(FilterSelectionDto filter) {
		return repository.findByElementIdInAndAssetIdInAndEndDateAfterAndStartDateBefore(filter.getElementIds(),
				filter.getAssetIds(), filter.getRangeStart(), filter.getRangeEnd());
	}

	@Override
	public List<RangeDecimalDataEntity> getData(List<Long> elementIds, long assetId, long sourceId, Instant startDate,
			Instant endDate) {
		return repository.findByElementIdInAndAssetIdAndSourceIdAndStartDateAndEndDate(elementIds, assetId, sourceId,
				startDate, endDate);
	}

	@Override
	public List<RangeDecimalDataEntity> getData(long assetId, long sourceId, long elementId, List<Instant> startDates) {
		return ListUtils.partition(startDates, partitionSizeForInClause)
		 .stream()
		 .map(batchedDates -> repository.findByAssetIdAndSourceIdAndElementIdAndStartDateIn(assetId, sourceId, elementId, batchedDates))
		 .flatMap(Collection::stream)
		 .collect(toList());
	}

	@Override
	public List<RangeDecimalDataDto> findApplicableForDate(long elementId, long assetId, Instant checkDate) {
		return repository.findApplicableForDate(elementId, assetId, checkDate).stream().map(this::map).collect(toList());
	}
}
