package com.oak.dto;

import java.math.BigDecimal;
import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.enums.DataCategory;
import com.oak.enums.MeasurementType;

public class GenericDataDto {
	@IsNull(groups = CreateValidationGroup.class, value = "Data Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Data Id")
	private Long id;
	
	@IsNotNull("Measurement Type") 
	private MeasurementType measurementType;

	@IsNotNull("Data Category") 
	private DataCategory dataCategory;

	@IsNotNull("Asset Id") 
	private Long assetId;

	@IsNotNull("Element Id")
	private Long elementId;

	@IsNotNull("Source Id")
	private Long sourceId;

	@IsNotNull("Reading/Start Date")
	private Instant readingDate;
	
	private Instant endDate;
	private BigDecimal decimalReading;
	private Long unitId;
	private String textReading;
	private boolean booleanReading;
	private Long uploadRecordId;
	private Long linkRecordId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public MeasurementType getMeasurementType() {
		return measurementType;
	}
	
	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}
	
	public DataCategory getDataCategory() {
		return dataCategory;
	}
	
	public void setDataCategory(DataCategory dataCategory) {
		this.dataCategory = dataCategory;
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
	
	public Instant getReadingDate() {
		return readingDate;
	}
	
	public void setReadingDate(Instant readingDate) {
		this.readingDate = readingDate;
	}
	
	public Instant getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}
	
	public BigDecimal getDecimalReading() {
		return decimalReading;
	}
	
	public void setDecimalReading(BigDecimal decimalReading) {
		this.decimalReading = decimalReading;
	}
	
	public Long getUnitId() {
		return unitId;
	}
	
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	
	public String getTextReading() {
		return textReading;
	}
	
	public void setTextReading(String textReading) {
		this.textReading = textReading;
	}
	
	public boolean isBooleanReading() {
		return booleanReading;
	}
	
	public void setBooleanReading(boolean booleanReading) {
		this.booleanReading = booleanReading;
	}
	
	public Long getUploadRecordId() {
		return uploadRecordId;
	}
	
	public void setUploadRecordId(Long uploadRecordId) {
		this.uploadRecordId = uploadRecordId;
	}
	
	public Long getLinkRecordId() {
		return linkRecordId;
	}
	
	public void setLinkRecordId(Long linkRecordId) {
		this.linkRecordId = linkRecordId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("measurementType", measurementType).append("dataCategory", dataCategory)
				.append("assetId", assetId).append("elementId", elementId).append("sourceId", sourceId)
				.append("readingDate", readingDate).append("endDate", endDate).append("decimalReading", decimalReading)
				.append("unitId", unitId).append("textReading", textReading).append("booleanReading", booleanReading)
				.append("uploadRecordId", uploadRecordId).append("linkRecordId", linkRecordId).toString();
	}
}
