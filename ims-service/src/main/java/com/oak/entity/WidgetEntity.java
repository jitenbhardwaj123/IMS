package com.oak.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.NoNullElements;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.enums.WidgetType;

@Entity
@Table(name = "widget")
public class WidgetEntity extends Auditable implements Mergeable<WidgetEntity, Long> {
	@Id
	@SequenceGenerator(name = "widget_seq", sequenceName = "widget_seq", allocationSize = 1)
	@GeneratedValue(generator = "widget_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Enumerated(STRING)
	@Column(name = "widget_type")
	@IsNotNull("Widget Type")
	private WidgetType widgetType;

	@Column(name = "name")
	@IsNotBlank("Widget Name")
	private String name;

	@Column(name = "description")
	@IsNotBlank("Widget Description")
	private String description;

	@Column(name = "date_format")
	@IsNotBlank("Date Format")
	private String dateFormat;

	@Column(name = "reading_date_label")
	@IsNotBlank("Reading/Start Date Label")
	private String readingDateLabel;

	@Column(name = "end_date_label")
	@IsNotBlank("End Date Label")
	private String endDateLabel;

	@Column(name = "exportable")
	private boolean exportable;

	@Column(name = "emailable")
	private boolean emailable;

	@IsNotNull("Filter Criteria")
	@JoinColumn(name = "filter_criteria_id")
	@OneToOne(cascade = ALL, orphanRemoval = true)
	private FilterCriteriaEntity filterCriteria;

	@OneToMany
	@JoinTable(name = "widget_asset_type", joinColumns = {
			@JoinColumn(name = "widget_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "asset_type_id") })
	@NotEmptyAndNoNullElements("Widget Asset Types")
	private List<AssetTypeEntity> assetTypes = new ArrayList<>();

	@JoinColumn(name = "widget_id", nullable = false)
	@OneToMany(cascade = ALL, orphanRemoval = true, fetch = EAGER)
	@OrderColumn(name = "display_order")
	@NotEmptyAndNoNullElements("Widget Elements")
	private List<WidgetElementEntity> elements = new ArrayList<>();
	
	@JoinColumn(name = "widget_id", nullable = false)
	@OneToMany(cascade = ALL, orphanRemoval = true, fetch = EAGER)
	@OrderColumn(name = "sort_order")
	@NoNullElements("Sort Options")
	private List<WidgetItemSortOptionEntity> sortOptions = new ArrayList<>();

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

	public FilterCriteriaEntity getFilterCriteria() {
		return filterCriteria;
	}

	public void setFilterCriteria(FilterCriteriaEntity filterCriteria) {
		this.filterCriteria = filterCriteria;
	}

	public List<AssetTypeEntity> getAssetTypes() {
		return assetTypes;
	}

	public void setAssetTypes(List<AssetTypeEntity> assetTypes) {
		this.assetTypes.clear();
		if (isNotEmpty(assetTypes)) {
			this.assetTypes.addAll(assetTypes);
		}
	}

	public List<WidgetElementEntity> getElements() {
		return elements;
	}

	public void setElements(List<WidgetElementEntity> elements) {
		this.elements.clear();
		if (isNotEmpty(elements)) {
			this.elements.addAll(elements);
		}
	}

	public List<WidgetItemSortOptionEntity> getSortOptions() {
		return sortOptions;
	}

	public void setSortOptions(List<WidgetItemSortOptionEntity> sortOptions) {
		this.sortOptions.clear();
		if (isNotEmpty(sortOptions)) {
			this.sortOptions.addAll(sortOptions);
		}
	}

	@Override
	public WidgetEntity merge(WidgetEntity other) {
		if (other != null) {
			this.widgetType = other.widgetType;
			this.name = other.name;
			this.description = other.description;
			this.dateFormat = other.dateFormat;
			this.readingDateLabel = other.readingDateLabel;
			this.endDateLabel = other.endDateLabel;
			this.exportable = other.exportable;
			this.emailable = other.emailable;
			this.filterCriteria.merge(other.filterCriteria);
			this.setAssetTypes(other.assetTypes);
			this.setElements(other.elements);
			this.setSortOptions(other.sortOptions);
		}
		return this;
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
