package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotBlank;

@Entity
@Table(name = "filter_criteria")
public class FilterCriteriaEntity extends Auditable implements Mergeable<FilterCriteriaEntity, Long> {
	@Id
	@SequenceGenerator(name = "filter_crit_seq", sequenceName = "filter_crit_seq", allocationSize = 1)
	@GeneratedValue(generator = "filter_crit_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Filter Name")
	private String name;

	@Column(name = "include_nested_assets")
	private boolean includeNestedAssets;

	@Column(name = "default_search_days")
	private int defaultSearchDays;

	@Embedded
	private DateRangeFilterEntity dateRangeFilter;

	@Embedded
	private AssetFilterEntity assetFilter;

	@Embedded
	private ElementFilterEntity elementFilter;
	
	@Embedded
	private AggregationFilterEntity aggregationFilter;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public DateRangeFilterEntity getDateRangeFilter() {
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

	public void setDateRangeFilter(DateRangeFilterEntity dateRangeFilter) {
		this.dateRangeFilter = dateRangeFilter;
	}

	public AssetFilterEntity getAssetFilter() {
		return assetFilter;
	}

	public void setAssetFilter(AssetFilterEntity assetFilter) {
		this.assetFilter = assetFilter;
	}

	public ElementFilterEntity getElementFilter() {
		return elementFilter;
	}

	public void setElementFilter(ElementFilterEntity elementFilter) {
		this.elementFilter = elementFilter;
	}

	public AggregationFilterEntity getAggregationFilter() {
		return aggregationFilter;
	}

	public void setAggregationFilter(AggregationFilterEntity aggregationFilter) {
		this.aggregationFilter = aggregationFilter;
	}

	@Override
	public FilterCriteriaEntity merge(FilterCriteriaEntity other) {
		if (other != null) {
			this.name = other.name;
			this.includeNestedAssets = other.includeNestedAssets;
			this.defaultSearchDays = other.defaultSearchDays;
			this.dateRangeFilter = other.dateRangeFilter;
			this.assetFilter = other.assetFilter;
			this.elementFilter = other.elementFilter;
			this.aggregationFilter = other.aggregationFilter;
		}
		return this;
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
