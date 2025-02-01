package com.oak.dto;

import java.math.BigDecimal;
import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;

public class LimitDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Limit Id")
	@IsNotNull(groups = UpdateValidationGroup.class, value = "Limit Id")
	private Long id;

	@IsNotBlank("Limit Name")
	private String name;

	@IsNotNull("Asset Id")
	private Long assetId;

	@IsNotNull("Element Id")
	private Long elementId;

	@IsNotNull("Limit Type Id")
	private Long limitTypeId;

	@IsNotNull("Source Id")
	private Long sourceId;

	@IsNotNull("Start Date")
	private Instant startDate;

	private Instant endDate;
	private BigDecimal lowerLimit;
	private BigDecimal upperLimit;
	private Long unitId;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getLimitTypeId() {
		return limitTypeId;
	}

	public void setLimitTypeId(Long limitTypeId) {
		this.limitTypeId = limitTypeId;
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

	public BigDecimal getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(BigDecimal lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public BigDecimal getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(BigDecimal upperLimit) {
		this.upperLimit = upperLimit;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("assetId", assetId).append("elementId", elementId)
				.append("limitTypeId", limitTypeId).append("sourceId", sourceId).append("startDate", startDate)
				.append("endDate", endDate).append("lowerLimit", lowerLimit).append("upperLimit", upperLimit)
				.append("unitId", unitId).toString();
	}
}