package com.oak.entity;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class DateRangeFilterEntity {
	private boolean dateRangeFilterEnabled;

	public boolean isDateRangeFilterEnabled() {
		return dateRangeFilterEnabled;
	}

	public void setDateRangeFilterEnabled(boolean dateRangeFilterEnabled) {
		this.dateRangeFilterEnabled = dateRangeFilterEnabled;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("dateRangeFilterEnabled", dateRangeFilterEnabled).toString();
	}
}
