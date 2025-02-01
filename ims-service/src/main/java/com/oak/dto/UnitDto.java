package com.oak.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IdentifiableExists;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.constraint.ObjectAndPropertyNotNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.entity.UnitTypeEntity;

public class UnitDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Unit Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Unit Id")
	private Long id;

	@IsNotBlank("Unit Name")
	private String name;

	@IsNotBlank("Unit Description")
	private String description;

	@ObjectAndPropertyNotNull(name = "Unit Type", property = "id")
	@IdentifiableExists(UnitTypeEntity.class)
	private UnitTypeDto unitType;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UnitTypeDto getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitTypeDto unitType) {
		this.unitType = unitType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).append("unitType", unitType).toString();
	}

}