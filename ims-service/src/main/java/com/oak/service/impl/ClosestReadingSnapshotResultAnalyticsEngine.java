package com.oak.service.impl;

import static com.oak.enums.AnalyticsEngineType.CLOSEST_READING_SNAPSHOT_RESULT;
import static java.lang.String.format;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.join;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.cdc.Operation;
import com.oak.common.exception.model.ApiException;
import com.oak.dto.AnalyticsProfileDto;
import com.oak.dto.AnalyticsProfileTemplateResultElementDto;
import com.oak.dto.ElementDto;
import com.oak.dto.ExecutionResult;
import com.oak.dto.RangeBooleanDataDto;
import com.oak.dto.RangeDecimalDataDto;
import com.oak.dto.RangeTextDataDto;
import com.oak.dto.SnapshotBooleanDataDto;
import com.oak.dto.SnapshotDecimalDataDto;
import com.oak.dto.SnapshotTextDataDto;
import com.oak.dto.SourceDto;
import com.oak.enums.AnalyticsEngineType;
import com.oak.enums.DataCategory;
import com.oak.enums.MeasurementType;
import com.oak.service.AnalyticsEngine;
import com.oak.service.AnalyticsEngineTemplateExecutor;
import com.oak.service.RangeBooleanDataService;
import com.oak.service.RangeDecimalDataService;
import com.oak.service.RangeTextDataService;
import com.oak.service.SnapshotBooleanDataService;
import com.oak.service.SnapshotDecimalDataService;
import com.oak.service.SnapshotTextDataService;
import com.oak.service.SourceService;

@Service
@Transactional
public class ClosestReadingSnapshotResultAnalyticsEngine implements AnalyticsEngine {
	private static final String MEASUREMENT_TYPE_NOT_SUPPORTED = "Analytics Engine for Snapshot %s data is not supported yet";
	private static final String DATA_CATEGORY_NOT_SUPPORTED = "Analytics Engine for Data Category %s is not supported yet";

	@Autowired
	private Logger logger;
	
	@Autowired
	private SourceService sourceService;
	
	@Autowired
	private AnalyticsEngineTemplateExecutor templateExecutor;
	
	@Autowired
	private RangeDecimalDataService rangeDecimalDataService;
	
	@Autowired
	private RangeTextDataService rangeTextDataService;
	
	@Autowired
	private RangeBooleanDataService rangeBooleanDataService;
	
	@Autowired
	private SnapshotDecimalDataService snapshotDecimalDataService;
	
	@Autowired
	private SnapshotTextDataService snapshotTextDataService;
	
	@Autowired
	private SnapshotBooleanDataService snapshotBooleanDataService;
	
	@Override
	public final AnalyticsEngineType getEngineType() {
		return CLOSEST_READING_SNAPSHOT_RESULT;
	}

	@Override
	public void executeConfigEvent(Operation operation, AnalyticsProfileDto profile) {
		logger.debug("executeConfigEvent(Operation {}, AnalyticsProfileDto {})", operation, profile.getName());
		
		cleanUpExistingSnapshotResults(profile);
	}
	
	@Override
	public void executeSnapshotEvent(Operation operation,
			AnalyticsProfileDto profile,
			long assetId, long elementId, 
			long sourceId, Instant readingDate) {
		logger.debug("executeSnapshotEvent(Operation {}, AnalyticsProfileDto {}, assetId {}, elementId {}, sourceId {}, readingDate {})", 
				operation, profile.getName(), assetId, elementId, sourceId, readingDate);
		
		SourceDto source = sourceService.getAnalyticsSource();
		cleanUpExistingSnapshotResultsForSnapshotChanges(profile, assetId, readingDate);
		
		if (Operation.CREATE == operation || Operation.UPDATE == operation) {
			ExecutionResult result = templateExecutor.executeTemplate(profile.getTemplate(), getTemplateInputs(profile, assetId, readingDate));
			logger.debug("RESULT: {}", result);
			
			if (result != null && !result.getResults().isEmpty()) {
				profile.getResultElements().forEach(element -> {
					if (result.getResults().containsKey(element.getKey())) {
						saveResult(assetId, source.getId(), element, result);
					}
				});
			}
		}
	}

	@Override
	public void executeRangeEvent(Operation operation,
			AnalyticsProfileDto profile,
			long assetId, long elementId, 
			long sourceId, Instant readingDate,
			Instant endDate) {
		logger.debug("executeRangeEvent(Operation {}, AnalyticsProfileDto {}, assetId {}, elementId {}, sourceId {}, readingDate {}, endDate {})", 
				operation, profile.getName(), assetId, elementId, sourceId, readingDate, endDate);
		
		cleanUpExistingSnapshotResultsForRangeChanges(profile, assetId, readingDate, endDate);
	}

	private void cleanUpExistingSnapshotResults(AnalyticsProfileDto profile) {
		profile.getResultElements().stream().map(AnalyticsProfileTemplateResultElementDto::getElement)
			.collect(groupingBy(ElementDto::getMeasurementType, mapping(ElementDto::getId, toList())))
			.forEach(this::deleteSnapshotData);
	}
	
	private void cleanUpExistingSnapshotResultsForSnapshotChanges(AnalyticsProfileDto profile, long assetId, Instant readingDate) {
		profile.getResultElements().stream().map(AnalyticsProfileTemplateResultElementDto::getElement)
			.collect(groupingBy(ElementDto::getMeasurementType, mapping(ElementDto::getId, toList())))
			.forEach( (measurementType, elementIds) -> deleteSnapshotData(measurementType, elementIds, assetId, readingDate));
	}
	
	private void cleanUpExistingSnapshotResultsForRangeChanges(AnalyticsProfileDto profile, long assetId, Instant rangeStart, Instant rangeEnd) {
		profile.getResultElements().stream().map(AnalyticsProfileTemplateResultElementDto::getElement)
			.collect(groupingBy(ElementDto::getMeasurementType, mapping(ElementDto::getId, toList())))
			.forEach( (measurementType, elementIds) -> deleteSnapshotDataForRange(measurementType, elementIds, assetId, rangeStart, rangeEnd));
	}
	
	private void deleteSnapshotData(MeasurementType measurementType, List<Long> elementIds) {
		switch (measurementType) {
			case DECIMAL:
				snapshotDecimalDataService.deleteByElementIdIn(elementIds);
				break;
			case BOOLEAN_OPTIONS:
				snapshotBooleanDataService.deleteByElementIdIn(elementIds);
				break;
			case TEXT:
			case TEXT_OPTIONS:
				snapshotTextDataService.deleteByElementIdIn(elementIds);
				break;
			default:
				throw new ApiException(format(MEASUREMENT_TYPE_NOT_SUPPORTED, measurementType));
		}
	}
	
	private void deleteSnapshotData(MeasurementType measurementType, List<Long> elementIds, long assetId, Instant readingDate) {
		switch (measurementType) {
			case DECIMAL:
				snapshotDecimalDataService.deleteByElementIdInAndAssetIdAndReadingDate(elementIds, assetId, readingDate);
				break;
			case BOOLEAN_OPTIONS:
				snapshotBooleanDataService.deleteByElementIdInAndAssetIdAndReadingDate(elementIds, assetId, readingDate);
				break;
			case TEXT:
			case TEXT_OPTIONS:
				snapshotTextDataService.deleteByElementIdInAndAssetIdAndReadingDate(elementIds, assetId, readingDate);
				break;
			default:
				throw new ApiException(format(MEASUREMENT_TYPE_NOT_SUPPORTED, measurementType));
		}
	}
	
	private void deleteSnapshotDataForRange(MeasurementType measurementType, List<Long> elementIds, long assetId, Instant rangeStart, Instant rangeEnd) {
		switch (measurementType) {
			case DECIMAL:
				snapshotDecimalDataService.deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(elementIds, assetId, rangeStart, rangeEnd);
				break;
			case BOOLEAN_OPTIONS:
				snapshotBooleanDataService.deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(elementIds, assetId, rangeStart, rangeEnd);
				break;
			case TEXT:
			case TEXT_OPTIONS:
				snapshotTextDataService.deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(elementIds, assetId, rangeStart, rangeEnd);
				break;
			default:
				throw new ApiException(format(MEASUREMENT_TYPE_NOT_SUPPORTED, measurementType));
		}
	}
	
	private List<Object> getTemplateInputs(AnalyticsProfileDto profile, long assetId, Instant readingDate) {
		Map<String, Object> inputKeys = new HashMap<>();
		profile.getInputElements().forEach(e -> inputKeys.put(e.getKey(), getReading(e.getDataCategory(), e.getElement(), assetId, readingDate)));
		profile.getInputStrings().forEach(s -> inputKeys.put(s.getKey(), s.getValue()));
		
		List<Object> inputs = new ArrayList<>();
		profile.getTemplate().getParams().forEach(param -> {
			if (inputKeys.containsKey(param)) {
				inputs.add(inputKeys.get(param));
			} else {
				throw new RuntimeException(String.format("Missing Template param [ %s ] configuration for the profile [ %s ]", param, profile.getTemplate().getName()));
			}
		});
		
		return inputs;
	}

	@SuppressWarnings("unchecked")
	private Object getReading(DataCategory dataCategory, ElementDto element, long assetId, Instant readingDate) {
		List<Object> readings;
		if (DataCategory.RANGE == dataCategory) {
			readings = (List<Object>) getApplicableRangeData(element.getMeasurementType(), element.getId(), assetId, readingDate);
		} else {
			readings = (List<Object>) getClosestSnapshotData(element.getMeasurementType(), element.getId(), assetId, readingDate);
		}
		// TODO: pick the reading with highest source priority
		return CollectionUtils.isNotEmpty(readings) ? readings.get(0) : null;
	}
	
	private List<? extends Object> getClosestSnapshotData(MeasurementType measurementType, Long elementId, long assetId, Instant readingDate) {
		switch (measurementType) {
			case DECIMAL:
				return snapshotDecimalDataService.findClosestReading(assetId, elementId, readingDate);
			case BOOLEAN_OPTIONS:
				return snapshotBooleanDataService.findClosestReading(assetId, elementId, readingDate);
			case TEXT:
			case TEXT_OPTIONS:
				return snapshotTextDataService.findClosestReading(assetId, elementId, readingDate);
			default:
				throw new ApiException(format(MEASUREMENT_TYPE_NOT_SUPPORTED, measurementType));
		}
	}
	
	private List<? extends Object> getApplicableRangeData(MeasurementType measurementType, Long elementId, long assetId, Instant readingDate) {
		switch (measurementType) {
			case DECIMAL:
				return rangeDecimalDataService.findApplicableForDate(elementId, assetId, readingDate);
			case BOOLEAN_OPTIONS:
				return rangeBooleanDataService.findApplicableForDate(elementId, assetId, readingDate);
			case TEXT:
			case TEXT_OPTIONS:
				return rangeTextDataService.findApplicableForDate(elementId, assetId, readingDate);
			default:
				throw new ApiException(format(MEASUREMENT_TYPE_NOT_SUPPORTED, measurementType));
		}
	}

	private void saveResult(long assetId, long sourceId, AnalyticsProfileTemplateResultElementDto element, ExecutionResult result) {
		switch (element.getDataCategory()) {
			case SNAPSHOT:
				saveSnapshotData(assetId, sourceId, element, result);
				break;
			case RANGE:
				saveRangeData(assetId, sourceId, element, result);
				break;
			default:
				throw new ApiException(format(DATA_CATEGORY_NOT_SUPPORTED, element.getDataCategory()));
		}
	}

	private void saveSnapshotData(long assetId, long sourceId, AnalyticsProfileTemplateResultElementDto element,
			ExecutionResult result) {
		switch (element.getElement().getMeasurementType()) {
			case DECIMAL:
				SnapshotDecimalDataDto decimalData = new SnapshotDecimalDataDto();
				decimalData.setAssetId(assetId);
				decimalData.setElementId(element.getElement().getId());
				decimalData.setSourceId(sourceId);
				decimalData.setReadingDate(result.getApplicableDate());
				decimalData.setReading((BigDecimal) result.getResults().get(element.getKey()));
				snapshotDecimalDataService.create(decimalData);
				break;
			case BOOLEAN_OPTIONS:
				SnapshotBooleanDataDto booleanData = new SnapshotBooleanDataDto();
				booleanData.setAssetId(assetId);
				booleanData.setElementId(element.getElement().getId());
				booleanData.setSourceId(sourceId);
				booleanData.setReadingDate(result.getApplicableDate());
				booleanData.setReading((Boolean) result.getResults().get(element.getKey()));
				snapshotBooleanDataService.create(booleanData);
				break;
			case TEXT_OPTIONS:
				String textResult = (String) result.getResults().get(element.getKey());
				if (! element.getElement().getTextOptions().contains(textResult)) {
					throw new ApiException("Execution result [ %s ] is not present in the applicable options [ %s ]", 
							join(element.getElement().getTextOptions(), ", "));
				}
				SnapshotTextDataDto textOptionData = new SnapshotTextDataDto();
				textOptionData.setAssetId(assetId);
				textOptionData.setElementId(element.getElement().getId());
				textOptionData.setSourceId(sourceId);
				textOptionData.setReadingDate(result.getApplicableDate());
				textOptionData.setReading((String) result.getResults().get(element.getKey()));
				snapshotTextDataService.create(textOptionData);
				break;
			case TEXT:
				SnapshotTextDataDto textData = new SnapshotTextDataDto();
				textData.setAssetId(assetId);
				textData.setElementId(element.getElement().getId());
				textData.setSourceId(sourceId);
				textData.setReadingDate(result.getApplicableDate());
				textData.setReading((String) result.getResults().get(element.getKey()));
				snapshotTextDataService.create(textData);
				break;
			default:
				throw new ApiException(format(MEASUREMENT_TYPE_NOT_SUPPORTED, element.getElement().getMeasurementType()));
		}
	}

	private void saveRangeData(long assetId, long sourceId, AnalyticsProfileTemplateResultElementDto element,
			ExecutionResult result) {
		switch (element.getElement().getMeasurementType()) {
			case DECIMAL:
				RangeDecimalDataDto decimalData = new RangeDecimalDataDto();
				decimalData.setAssetId(assetId);
				decimalData.setElementId(element.getElement().getId());
				decimalData.setSourceId(sourceId);
				decimalData.setStartDate(result.getApplicableDate());
				decimalData.setEndDate(result.getEndDate());
				decimalData.setReading((BigDecimal) result.getResults().get(element.getKey()));
				rangeDecimalDataService.create(decimalData);
				break;
			case BOOLEAN_OPTIONS:
				RangeBooleanDataDto booleanData = new RangeBooleanDataDto();
				booleanData.setAssetId(assetId);
				booleanData.setElementId(element.getElement().getId());
				booleanData.setSourceId(sourceId);
				booleanData.setStartDate(result.getApplicableDate());
				booleanData.setEndDate(result.getEndDate());
				booleanData.setReading((Boolean) result.getResults().get(element.getKey()));
				rangeBooleanDataService.create(booleanData);
				break;
			case TEXT_OPTIONS:
				String textResult = (String) result.getResults().get(element.getKey());
				if (! element.getElement().getTextOptions().contains(textResult)) {
					throw new ApiException("Execution result [ %s ] is not present in the applicable options [ %s ]", 
							join(element.getElement().getTextOptions(), ", "));
				}
				RangeTextDataDto textOptionData = new RangeTextDataDto();
				textOptionData.setAssetId(assetId);
				textOptionData.setElementId(element.getElement().getId());
				textOptionData.setSourceId(sourceId);
				textOptionData.setStartDate(result.getApplicableDate());
				textOptionData.setEndDate(result.getEndDate());
				textOptionData.setReading((String) result.getResults().get(element.getKey()));
				rangeTextDataService.create(textOptionData);
				break;
			case TEXT:
				RangeTextDataDto textData = new RangeTextDataDto();
				textData.setAssetId(assetId);
				textData.setElementId(element.getElement().getId());
				textData.setSourceId(sourceId);
				textData.setStartDate(result.getApplicableDate());
				textData.setEndDate(result.getEndDate());
				textData.setReading((String) result.getResults().get(element.getKey()));
				rangeTextDataService.create(textData);
				break;
			default:
				throw new ApiException(format(MEASUREMENT_TYPE_NOT_SUPPORTED, element.getElement().getMeasurementType()));
		}
	}
}
