package com.oak.dto;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LineChartData {
	private Long unitId;
	private List<LineChartAssetData> assetData = new ArrayList<>();
	
	public LineChartData() {
	}

	public LineChartData(Long unitId) {
		this.unitId = unitId;
	}

	public LineChartData(Long unitId, List<LineChartAssetData> assetData) {
		this.unitId = unitId;
		this.assetData = assetData;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public List<LineChartAssetData> getAssetData() {
		return assetData;
	}

	public void setAssetData(List<LineChartAssetData> assetData) {
		this.assetData.clear();
		if (isNotEmpty(assetData)) {
			this.assetData = assetData;
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
		LineChartData castOther = (LineChartData) other;
		return new EqualsBuilder().append(unitId, castOther.unitId).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(unitId).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("unitId", unitId).append("assetData", assetData).toString();
	}
}
