package com.oak.dto;

import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;

public class RangeTextDataDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Range Data Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Range Data Id")
	private Long id;

	@IsNotNull("Asset Id")
	private Long assetId;

	@IsNotNull("Element Id")
	private Long elementId;

	@IsNotNull("Source Id")
	private Long sourceId;

	@IsNotNull("Start Date")
	private Instant startDate;
	
	private Instant endDate;
	private String reading;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("assetId", assetId).append("elementId", elementId).append("sourceId", sourceId)
				.append("startDate", startDate).append("endDate", endDate).append("reading", reading).toString();
	}

}