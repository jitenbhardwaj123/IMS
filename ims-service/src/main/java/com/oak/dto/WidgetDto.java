package com.oak.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.constraint.NoNullElements;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.enums.WidgetType;

public class WidgetDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Widget Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Widget Id")
	private Long id;
	
	@IsNotNull("Widget Type")
	private WidgetType widgetType;

	@IsNotBlank("Widget Name")
	private String name;

	@IsNotBlank("Widget Description")
	private String description;
	
	@IsNotBlank("Date Format")
	private String dateFormat;

	@IsNotBlank("Reading/Start Date Label")
	private String readingDateLabel;

	@IsNotBlank("End Date Label")
	private String endDateLabel;

	private boolean exportable;

	private boolean emailable;
	
	@Valid
    @IsNotNull("Filter Criteria")
	private FilterCriteriaDto filterCriteria;

	@NotEmptyAndNoNullElements("Widget Asset Types")
	private List<AssetTypeDto> assetTypes = new ArrayList<>();

	@Valid
	@NotEmptyAndNoNullElements("Widget Elements")
	private List<WidgetElementDto> elements = new ArrayList<>();
	
	@Valid
	@NoNullElements("Sort Options")
	private List<WidgetItemSortOptionDto> sortOptions = new ArrayList<>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public WidgetType getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(WidgetType widgetType) {
		this.widgetType = widgetType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getReadingDateLabel() {
		return readingDateLabel;
	}

	public void setReadingDateLabel(String readingDateLabel) {
		this.readingDateLabel = readingDateLabel;
	}

	public String getEndDateLabel() {
		return endDateLabel;
	}

	public void setEndDateLabel(String endDateLabel) {
		this.endDateLabel = endDateLabel;
	}

	public boolean isExportable() {
		return exportable;
	}

	public void setExportable(boolean exportable) {
		this.exportable = exportable;
	}

	public boolean isEmailable() {
		return emailable;
	}

	public void setEmailable(boolean emailable) {
		this.emailable = emailable;
	}

	public FilterCriteriaDto getFilterCriteria() {
		return filterCriteria;
	}

	public void setFilterCriteria(FilterCriteriaDto filterCriteria) {
		this.filterCriteria = filterCriteria;
	}

	public List<AssetTypeDto> getAssetTypes() {
		return assetTypes;
	}

	public void setAssetTypes(List<AssetTypeDto> assetTypes) {
		this.assetTypes = assetTypes;
	}

	public List<WidgetElementDto> getElements() {
		return elements;
	}

	public void setElements(List<WidgetElementDto> elements) {
		this.elements = elements;
	}

	public List<WidgetItemSortOptionDto> getSortOptions() {
		return sortOptions;
	}

	public void setSortOptions(List<WidgetItemSortOptionDto> sortOptions) {
		this.sortOptions = sortOptions;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("widgetType", widgetType).append("name", name).append("description", description)
				.append("dateFormat", dateFormat).append("readingDateLabel", readingDateLabel)
				.append("endDateLabel", endDateLabel).append("exportable", exportable).append("emailable", emailable)
				.append("filterCriteria", filterCriteria).append("assetTypes", assetTypes).append("elements", elements)
				.append("sortOptions", sortOptions).toString();
	}
}
