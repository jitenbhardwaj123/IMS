package com.oak.service.impl;

import static com.oak.cdc.Operation.READ;
import static com.oak.enums.AnalyticsEngineType.CLOSEST_READING_SNAPSHOT_RESULT;
import static com.oak.enums.DataCategory.RANGE;
import static com.oak.enums.DataCategory.SNAPSHOT;
import static java.lang.String.format;
import static java.time.Instant.EPOCH;
import static java.time.temporal.ChronoUnit.MICROS;
import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oak.cdc.Operation;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotEmpty;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.dto.AnalyticsProfileDto;
import com.oak.dto.AnalyticsProfileTemplateInputElementDto;
import com.oak.dto.AnalyticsProfileTemplateInputStringDto;
import com.oak.dto.AnalyticsProfileTemplateResultElementDto;
import com.oak.enums.AnalyticsEngineType;
import com.oak.enums.DataCategory;
import com.oak.service.AnalyticsEngine;
import com.oak.service.AnalyticsEngineTemplateService;
import com.oak.service.ElementService;

@Component
public class AnalyticsEngineChangeEventExecutor {
	/**
	 * snapshot decimal data entries compared against range decimal data
	 * driver element: a annulus pressure reading
	 * assumptions
	 * 	assuming data is in same units
	 * 		validate this
	 * 	when reading is deleted, existing status is deleted
	 * 	when limit is deleted/updated, existing statuses in that date range are deleted and re-evaluated
	 * 	when limit is added, readings in the date range are selected for evaluation
	 * 
	 * these conditions are evaluated in this order
	 * no status if no condition is applicable
	 * evaluation stops when an applicable condition is evaluated
	 * result options are derived from the element options
	 * 
	 * green if limit is null
	 * green if limit1 is null
	 * green if limit2 is null
	 * green if reading GTE 100 bar
	 * green if reading GTE 100 bar and LTE 200 bar
	 * green if reading GTE 70% of limit
	 * green if reading GTE 70% of limit1 and LTE 30% of limit2
	 * 
	 * 
	 * NULL_CHECK
	 * 	DataCategory
	 * 	MeasurementType
	 * 	ElementId
	 */

	/**
	 * Any changes to the profile or template should trigger a full recalc
	 * changes to the data triggers specific recalc
	 */
	
	/**
	 * SAme units assumed
	 * pass multiple records to template to consider data from multiple sources
	 */

	private final Logger logger;
	private final Map<AnalyticsEngineType, AnalyticsEngine> engines;
	private final ElementService elementService;
	private final AnalyticsEngineTemplateService templateService;
	private static final List<String> RANGE_TABLES = Arrays.asList("range_decimal_data","range_text_data","range_boolean_data");
	private static final List<String> SNAPSHOT_TABLES = Arrays.asList("snapshot_decimal_data","snapshot_text_data","snapshot_boolean_data");

	@Autowired
	public AnalyticsEngineChangeEventExecutor(Logger logger, Optional<List<AnalyticsEngine>> engines, ElementService elementService,
			AnalyticsEngineTemplateService templateService) {
		this.logger = logger;
		this.engines = new EnumMap<>(AnalyticsEngineType.class);
		this.engines.putAll(engines.orElseGet(ArrayList::new).stream().collect(toMap(AnalyticsEngine::getEngineType, identity())));
		String missing = Arrays.stream(AnalyticsEngineType.values())
				.filter(ty -> !this.engines.containsKey(ty))
				.map(AnalyticsEngineType::toString)
				.collect(joining(", "));
		if (isNotBlank(missing)) {
			throw new RuntimeException(format("Analytics engine not found for the types [ %s ]", missing));
		}
		this.elementService = elementService;
		this.templateService = templateService;
	}

	public void process(
			@IsNotNull("Change Event Operation") Operation operation, 
			@IsNotBlank("Change Event Table Name") String tableName,
			@IsNotEmpty("Change Event Values")
			Map<String, Object> values) {
		if ("analytics_engine_template".equalsIgnoreCase(tableName)) {
			processTemplateChanges(operation, tableName, values);
		} else if ("analytics_profile".equalsIgnoreCase(tableName)) {
			processProfileChanges(operation, tableName, values);
		} else {
			processReadingChanges(operation, tableName, values);
		}
	}

	// TODO: implement caching
	public void processTemplateChanges(Operation operation, String tableName, Map<String, Object> values) {
		// TODO: find all the related profiles based on the changed template id
		List<AnalyticsProfileDto> profiles = getDummyProfile();
		profiles.stream().forEach(profile -> engines.get(profile.getEngineType()).executeConfigEvent(operation, profile));
	}
	
	// TODO: implement caching
	public void processProfileChanges(Operation operation, String tableName, Map<String, Object> values) {
		// TODO: find the profile based on the changed profile id
		AnalyticsProfileDto profile = getDummyProfile().get(0);
		engines.get(profile.getEngineType()).executeConfigEvent(operation, profile);
	}

	// TODO: implement caching
	private void processReadingChanges(Operation operation, String tableName, Map<String, Object> values) {
		// do not process reads (result of the application start-up catching up on changes)
		if (READ != operation) {
			DataCategory dataCategory = getReadingCategory(tableName);
			Long elementId = getLong(values, "element_id");
			// TODO: find the profiles based on the element id & data category pair
			List<AnalyticsProfileDto> profiles = getDummyProfile();
			Long assetId = getLong(values, "asset_id");
			Long sourceId = getLong(values, "source_id");
			Instant readingDate = getInstant(values, getReadingDateColumn(dataCategory));
			if (RANGE == dataCategory) {
				Instant endDate = getInstant(values, "end_date");
				profiles.stream().forEach(profile -> engines.get(profile.getEngineType())
						.executeRangeEvent(operation, profile, assetId, elementId, sourceId, readingDate, endDate));
			} else {
				profiles.stream().forEach(profile -> engines.get(profile.getEngineType())
						.executeSnapshotEvent(operation, profile, assetId, elementId, sourceId, readingDate));
			}
		}
	}

	private DataCategory getReadingCategory(String tableName) {
		if (RANGE_TABLES.contains(tableName.toLowerCase())) {
			return RANGE;
		} else if (SNAPSHOT_TABLES.contains(tableName.toLowerCase())) {
			return SNAPSHOT;
		}
		throw new RuntimeException(format("Could not identify the data category for the table name [ %s ]", tableName));
	}
	
	private Long getLong(Map<String, Object> values, String key) {
		if (isNotBlank(key) && values.containsKey(key)) {
			return (Long) values.get(key);
		}
		return null;
	}

	private Instant getInstant(Map<String, Object> values, String readingDateColumn) {
		Long millis = getLong(values, readingDateColumn);
		return millis != null ? EPOCH.plus(millis, MICROS) : null;
	}

	private String getReadingDateColumn(DataCategory dataCategory) {
		if (RANGE == dataCategory) {
			return "start_date";
		} else {
			return "reading_date";
		}
	}
		
	private List<AnalyticsProfileDto> getDummyProfile() {
		AnalyticsProfileDto profile = new AnalyticsProfileDto();
		profile.setName("Annulus Pressure Analytics Profile");
		
		profile.setEngineType(CLOSEST_READING_SNAPSHOT_RESULT);
		profile.setTemplate(templateService.read(3L));
		
		profile.getInputElements().add(new AnalyticsProfileTemplateInputElementDto(elementService.read(134L), SNAPSHOT, "reading"));
		profile.getInputElements().add(new AnalyticsProfileTemplateInputElementDto(elementService.read(135L), RANGE, "lowLimit"));
		profile.getInputElements().add(new AnalyticsProfileTemplateInputElementDto(elementService.read(136L), RANGE, "highLimit"));
		
		profile.getInputStrings().add(new AnalyticsProfileTemplateInputStringDto("absoluteMaxLimit", "300"));

		profile.getResultElements().add(new AnalyticsProfileTemplateResultElementDto(elementService.read(137L), SNAPSHOT, "STATUS"));
		profile.getResultElements().add(new AnalyticsProfileTemplateResultElementDto(elementService.read(138L), SNAPSHOT, "STATUS_REASON"));
		
		return asList(profile);
	}
}
