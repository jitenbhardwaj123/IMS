package com.oak.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;

public class PageDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Page Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Page Id")
	private Long id;

	@IsNotBlank("Page Name")
	private String name;
	
	private boolean dashboard;

	@NotEmptyAndNoNullElements("Page Asset Types")
	private List<AssetTypeDto> assetTypes = new ArrayList<>();

	private List<WidgetDto> widgets = new ArrayList<>();

	private List<PageDto> children = new ArrayList<>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDashboard() {
		return dashboard;
	}

	public void setDashboard(boolean dashboard) {
		this.dashboard = dashboard;
	}

	public List<AssetTypeDto> getAssetTypes() {
		return assetTypes;
	}

	public void setAssetTypes(List<AssetTypeDto> assetTypes) {
		this.assetTypes = assetTypes;
	}

	public List<WidgetDto> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<WidgetDto> widgets) {
		this.widgets = widgets;
	}

	public List<PageDto> getChildren() {
		return children;
	}

	public void setChildren(List<PageDto> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("dashboard", dashboard).append("assetTypes", assetTypes)
				.append("widgets", widgets).append("children", children).toString();
	}
}
