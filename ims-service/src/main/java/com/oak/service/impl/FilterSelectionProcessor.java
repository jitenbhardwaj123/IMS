package com.oak.service.impl;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oak.dto.AssetDto;
import com.oak.dto.FilterCriteriaDto;
import com.oak.dto.FilterSelectionDto;
import com.oak.mapper.FilterSelectionMapper;
import com.oak.service.AssetService;

@Component
public class FilterSelectionProcessor {
	private final Logger logger;
	private final AssetService assetService;
	private final FilterSelectionMapper filterSelectionMapper;

	@Autowired
	public FilterSelectionProcessor(Logger logger, AssetService assetService,
			FilterSelectionMapper filterSelectionMapper) {
		super();
		this.logger = logger;
		this.assetService = assetService;
		this.filterSelectionMapper = filterSelectionMapper;
	}

	public FilterSelectionDto apply(FilterCriteriaDto criteria, FilterSelectionDto selection, Long currentAssetId) {
		logger.info("Original Filter Selection: {}", selection);

		FilterSelectionDto finalSelection = filterSelectionMapper.copy(selection);
		applySearchDates(criteria, finalSelection);
		applyAssets(criteria, finalSelection, currentAssetId);
		applyAggregationOptions(criteria, finalSelection);

		logger.info("Final Filter Selection: {}", finalSelection);
		return finalSelection;
	}

	private void applySearchDates(FilterCriteriaDto criteria, FilterSelectionDto selection) {
		if (selection.getRangeStart() == null || selection.getRangeEnd() == null) {
			Instant rangeEnd = Instant.now();
			Instant rangeStart = rangeEnd.minus(criteria.getDefaultSearchDays(), DAYS);
			selection.setRangeStart(rangeStart);
			selection.setRangeEnd(rangeEnd);
		}
	}

	private void applyAssets(FilterCriteriaDto criteria, FilterSelectionDto selection, Long currentAssetId) {
		if (selection.getAssetIds().isEmpty()) {
			selection.setAssetIds(asList(currentAssetId));
		}

		if (criteria.isIncludeNestedAssets()) {
			List<AssetDto> assets = this.assetService.getAssetsByIds(selection.getAssetIds());
			selection.setAssetIds(new ArrayList<>(getNestedAssedIds(assets)));
		}
	}

	private Set<Long> getNestedAssedIds(List<AssetDto> assets) {
		Set<Long> assetIds = new HashSet<>();
		assets.forEach(asset -> {
			assetIds.add(asset.getId());
			assetIds.addAll(getNestedAssedIds(asset.getChildren()));
		});
		return assetIds;
	}

	private void applyAggregationOptions(FilterCriteriaDto criteria, FilterSelectionDto selection) {
		if (criteria.getAggregationFilter() != null && criteria.getAggregationFilter().isAggregationFilterEnabled()) {
			selection.setAggregationType(defaultIfNull(selection.getAggregationType(),
					criteria.getAggregationFilter().getDefaultAggregationType()));
			selection.setAggregationInterval(defaultIfNull(selection.getAggregationInterval(),
					criteria.getAggregationFilter().getDefaultAggregationInterval()));
			if (selection.getAggregationTime() <= 0) {
				selection.setAggregationTime(criteria.getAggregationFilter().getDefaultAggregationTime());
			}
		} else {
			selection.setAggregationType(null);
			selection.setAggregationInterval(null);
			selection.setAggregationTime(0);
		}
	}
}