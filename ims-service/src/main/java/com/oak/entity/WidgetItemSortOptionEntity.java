package com.oak.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.enums.SortDirection;
import com.oak.enums.WidgetItemType;

@Entity
@Table(name = "widget_item_sort_option")
public class WidgetItemSortOptionEntity implements Identifiable {
	@Id
	@SequenceGenerator(name = "widget_item_sort_option_seq", sequenceName = "widget_item_sort_option_seq", allocationSize = 1)
	@GeneratedValue(generator = "widget_item_sort_option_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Enumerated(STRING)
	@Column(name = "item_type")
	@IsNotNull("Widget Item Type")
	private WidgetItemType widgetItemType;

	@Column(name = "item")
	@IsNotBlank("Widget Item")
	private String item;

	@Enumerated(STRING)
	@Column(name = "sort_dir")
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
