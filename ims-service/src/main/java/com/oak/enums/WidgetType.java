package com.oak.enums;

import static com.oak.enums.DataCategory.RANGE;
import static com.oak.enums.DataCategory.SNAPSHOT;

public enum WidgetType {
	RANGE_TABLE(RANGE),
	SNAPSHOT_TABLE(SNAPSHOT),
	RANGE_TABLE_ACCORDION(RANGE),
	SNAPSHOT_TABLE_ACCORDION(SNAPSHOT),
	SNAPSHOT_LINE_CHART(SNAPSHOT),
	SNAPSHOT_BAR_CHART(SNAPSHOT);
	
	private WidgetType(DataCategory category) {
		this.category = category;
	}
	
	private final DataCategory category;
	
	public DataCategory getCategory() {
		return category;
	}
}
