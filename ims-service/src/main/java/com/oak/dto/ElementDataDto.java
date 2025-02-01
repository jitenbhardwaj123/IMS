package com.oak.dto;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;

public class ElementDataDto implements Identifiable {
	private Long id;
	private Long elementId;
	private BigDecimal decimalReading;
	private Long unitId;
	private String textReading;
	private boolean booleanReading;
	private Long uploadRecordId;
	private Long linkRecordId;
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getElementId() {
		return elementId;
	}
	
	public void setElementId(Long elementId) {
		this.elementId = elementId;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("elementId", elementId).append("decimalReading", decimalReading).append("unitId", unitId)
				.append("textReading", textReading).append("booleanReading", booleanReading)
				.append("uploadRecordId", uploadRecordId).append("linkRecordId", linkRecordId).toString();
	}
}