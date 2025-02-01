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
import com.oak.dto.SnapshotDecimalDataDto;
import com.oak.entity.SnapshotDecimalDataEntity;
import com.oak.mapper.SnapshotDecimalDataMapper;
import com.oak.repository.SnapshotDecimalDataRepository;
import com.oak.service.SnapshotDecimalDataService;

@Service
@Transactional
public class SnapshotDecimalDataServiceImpl implements SnapshotDecimalDataService {
	private final Logger logger;
	private final JdbcTemplate jdbcTemplate;
	private final SnapshotDecimalDataRepository repository;
	private final SnapshotDecimalDataMapper mapper;
	
	@Value("${sql.in-clause.maxsize}")
	private int partitionSizeForInClause;
	
	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int jdbcBatchSize;

	@Autowired
	public SnapshotDecimalDataServiceImpl(Logger logger, JdbcTemplate jdbcTemplate, SnapshotDecimalDataRepository repository, SnapshotDecimalDataMapper mapper) {
		this.logger = logger;
		this.jdbcTemplate = jdbcTemplate;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public SnapshotDecimalDataDto create(SnapshotDecimalDataDto dto) {
		return commonCreate(dto);
	}

	@Override
	public void create(List<SnapshotDecimalDataDto> dtos) {
		repository.batchCreate(jdbcTemplate, dtos, jdbcBatchSize);
	}
	
	@Override
	public void update(List<SnapshotDecimalDataDto> dtos) {
		repository.batchUpdate(jdbcTemplate, dtos, jdbcBatchSize);
	}

	@Override
	public SnapshotDecimalDataDto update(SnapshotDecimalDataDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public SnapshotDecimalDataDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<SnapshotDecimalDataDto, SnapshotDecimalDataEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<SnapshotDecimalDataEntity, Long> getRepository() {
		return repository;
	}

	@Override
	public List<SnapshotDecimalDataEntity> getData(List<Long> elementIds, List<Long> assetIds) {
		return repository.findByElementIdInAndAssetIdIn(elementIds, assetIds);
	}

	@Override
	public List<SnapshotDecimalDataEntity> getData(FilterSelectionDto filter) {
		return repository.findByElementIdInAndAssetIdInAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(filter.getElementIds(),
				filter.getAssetIds(), filter.getRangeStart(), filter.getRangeEnd());
	}

	@Override
	public List<SnapshotDecimalDataEntity> getData(List<Long> elementIds, long assetId, long sourceId,
			Instant readingDate) {
		return repository.findByElementIdInAndAssetIdAndSourceIdAndReadingDate(elementIds, assetId, sourceId, readingDate);
	}

	@Override
	public List<SnapshotDecimalDataEntity> getData(long assetId, long sourceId, long elementId, List<Instant> readingDates) {
		return ListUtils.partition(readingDates, partitionSizeForInClause)
		 .stream()
		 .map(batchedDates -> repository.findByAssetIdAndSourceIdAndElementIdAndReadingDateIn(assetId, sourceId, elementId, batchedDates))
		 .flatMap(Collection::stream)
		 .collect(toList());
	}

	@Override
	public void deleteByElementIdIn(List<Long> elementIds) {
		repository.deleteByElementIdIn(elementIds);
	}

	@Override
	public void deleteByElementIdInAndAssetIdAndReadingDate(List<Long> elementIds, long assetId, Instant readingDate) {
		repository.deleteByElementIdInAndAssetIdAndReadingDate(elementIds, assetId, readingDate);
	}

	@Override
	public void deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(
			List<Long> elementIds, long assetId, Instant rangeStart, Instant rangeEnd) {
		if (rangeEnd == null) {
			repository.deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqual(elementIds, assetId, rangeStart);
		} else {
			repository.deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(elementIds, assetId, rangeStart, rangeEnd);
		}
	}

	@Override
	public List<SnapshotDecimalDataDto> findClosestReading(long assetId, long elementId, Instant readingDate) {
		return repository.findClosestReading(assetId, elementId, readingDate).stream().map(this::map).collect(toList());
	}
}
