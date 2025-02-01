package com.oak.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.enums.SortDirection;
import com.oak.enums.WidgetItemType;

public class WidgetItemSortOptionDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Widget Item Sort Option Id")
	private Long id;

	@IsNotNull("Widget Item Type")
	private WidgetItemType widgetItemType;

	@IsNotBlank("Widget Item")
	private String item;

	@IsNotNull("Item Sort Direction")
	private SortDirection sortDirection;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public WidgetItemType getWidgetItemType() {
		return widgetItemType;
	}

	public void setWidgetItemType(WidgetItemType widgetItemType) {
		this.widgetItemType = widgetItemType;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public SortDirection getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(SortDirection sortDirection) {
		this.sortDirection = sortDirection;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("widgetItemType", widgetItemType).append("item", item).append("sortDirection", sortDirection)
				.toString();
	}
}
