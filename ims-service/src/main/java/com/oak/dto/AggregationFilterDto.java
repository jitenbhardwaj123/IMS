package com.oak.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.enums.AggregationInterval;
import com.oak.enums.AggregationType;

public class AggregationFilterDto {
	private boolean aggregationFilterEnabled;
	private AggregationType defaultAggregationType;
	private AggregationInterval defaultAggregationInterval;
	private int defaultAggregationTime;
	
	public AggregationType getDefaultAggregationType() {
		return defaultAggregationType;
	}

	public void setDefaultAggregationType(AggregationType defaultAggregationType) {
		this.defaultAggregationType = defaultAggregationType;
	}

	public AggregationInterval getDefaultAggregationInterval() {
		return defaultAggregationInterval;
	}

	public void setDefaultAggregationInterval(AggregationInterval defaultAggregationInterval) {
		this.defaultAggregationInterval = defaultAggregationInterval;
	}

	public int getDefaultAggregationTime() {
		return defaultAggregationTime;
	}

	public void setDefaultAggregationTime(int defaultAggregationTime) {
		this.defaultAggregationTime = defaultAggregationTime;
	}

	public boolean isAggregationFilterEnabled() {
		return aggregationFilterEnabled;
	}
	
	public void setAggregationFilterEnabled(boolean aggregationFilterEnabled) {
		this.aggregationFilterEnabled = aggregationFilterEnabled;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("aggregationFilterEnabled", aggregationFilterEnabled)
				.append("defaultAggregationType", defaultAggregationType)
				.append("defaultAggregationInterval", defaultAggregationInterval)
				.append("defaultAggregationTime", defaultAggregationTime).toString();
	}
}
