package com.oak.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IdentifiableExists;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.constraint.ObjectAndPropertyNotNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.entity.AssetTypeEntity;

public class AssetDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Asset Id")
	private Long id;
	
	@IsNotBlank("Asset Name")
	private String name;
	
	@ObjectAndPropertyNotNull(name = "Asset Type", property = "id")
	@IdentifiableExists(AssetTypeEntity.class)
	private AssetTypeDto assetType;

	@Valid
	private List<AssetDto> children = new ArrayList<>();

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

	public AssetTypeDto getAssetType() {
		return assetType;
	}

	public void setAssetType(AssetTypeDto assetType) {
		this.assetType = assetType;
	}

	public List<AssetDto> getChildren() {
		return children;
	}

	public void setChildren(List<AssetDto> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("assetType", assetType)
				.append("children", children).toString();
	}
}