package com.oak.dto;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.group.CreateValidationGroup;

public class FilterCriteriaDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Filter Criteria Id")
	private Long id;

	@IsNotBlank("Filter Name")
	private String name;

	private boolean includeNestedAssets;
	private int defaultSearchDays;

	@Valid
	private DateRangeFilterDto dateRangeFilter;
	
	@Valid
	private AssetFilterDto assetFilter;

	@Valid
	private ElementFilterDto elementFilter;

	@Valid
	private AggregationFilterDto aggregationFilter;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public DateRangeFilterDto getDateRangeFilter() {
		return dateRangeFilter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isIncludeNestedAssets() {
		return includeNestedAssets;
	}

	public void setIncludeNestedAssets(boolean includeNestedAssets) {
		this.includeNestedAssets = includeNestedAssets;
	}

	public int getDefaultSearchDays() {
		return defaultSearchDays;
	}

	public void setDefaultSearchDays(int defaultSearchDays) {
		this.defaultSearchDays = defaultSearchDays;
	}

	public void setDateRangeFilter(DateRangeFilterDto dateRangeFilter) {
		this.dateRangeFilter = dateRangeFilter;
	}

	public AssetFilterDto getAssetFilter() {
		return assetFilter;
	}

	public void setAssetFilter(AssetFilterDto assetFilter) {
		this.assetFilter = assetFilter;
	}

	public ElementFilterDto getElementFilter() {
		return elementFilter;
	}

	public void setElementFilter(ElementFilterDto elementFilter) {
		this.elementFilter = elementFilter;
	}

	public AggregationFilterDto getAggregationFilter() {
		return aggregationFilter;
	}

	public void setAggregationFilter(AggregationFilterDto aggregationFilter) {
		this.aggregationFilter = aggregationFilter;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("includeNestedAssets", includeNestedAssets)
				.append("defaultSearchDays", defaultSearchDays).append("dateRangeFilter", dateRangeFilter)
				.append("assetFilter", assetFilter).append("elementFilter", elementFilter)
				.append("aggregationFilter", aggregationFilter).toString();
	}
}
