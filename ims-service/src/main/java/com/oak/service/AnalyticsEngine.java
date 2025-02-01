package com.oak.service;

import java.time.Instant;

import org.springframework.validation.annotation.Validated;

import com.oak.cdc.Operation;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.dto.AnalyticsProfileDto;
import com.oak.enums.AnalyticsEngineType;

@Validated
public interface AnalyticsEngine {
	AnalyticsEngineType getEngineType();

	void executeSnapshotEvent(
			@IsNotNull("Operation")
			Operation operation,
			@IsNotNull("Analytics Profile")
			AnalyticsProfileDto profile, 
			long assetId, 
			long elementId, 
			long sourceId,
			@IsNotNull("Reading Date")
			Instant readingDate);
	
	void executeRangeEvent(
			@IsNotNull("Operation")
			Operation operation,
			@IsNotNull("Analytics Profile")
			AnalyticsProfileDto profile, 
			long assetId, 
			long elementId, 
			long sourceId,
			@IsNotNull("Reading Date")
			Instant readingDate,
			Instant endDate);

	void executeConfigEvent(
			@IsNotNull("Operation")
			Operation operation,
			@IsNotNull("Analytics Profile")
			AnalyticsProfileDto profile);
}
