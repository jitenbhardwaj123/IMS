package com.oak.dto;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LineChartAssetData {
	private Long assetId;
	private List<LineChartSourceData> sourceData = new ArrayList<>();

	public LineChartAssetData() {
	}
	
	public LineChartAssetData(Long assetId) {
		this.assetId = assetId;
	}
	
	public LineChartAssetData(Long assetId, List<LineChartSourceData> sourceData) {
		this.assetId = assetId;
		this.sourceData = sourceData;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public List<LineChartSourceData> getSourceData() {
		return sourceData;
	}

	public void setSourceData(List<LineChartSourceData> sourceData) {
		this.sourceData.clear();
		if (isNotEmpty(sourceData)) {
			this.sourceData = sourceData;
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (!getClass().equals(other.getClass())) {
			return false;
		}
		LineChartAssetData castOther = (LineChartAssetData) other;
		return new EqualsBuilder().append(assetId, castOther.assetId).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(assetId).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("assetId", assetId).append("sourceData", sourceData).toString();
	}
}
