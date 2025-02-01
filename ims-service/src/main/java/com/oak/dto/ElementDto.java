package com.oak.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IdentifiableExists;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.constraint.NoNullElements;
import com.oak.common.validation.constraint.ObjectAndPropertyNotNull;
import com.oak.common.validation.constraint.PropertyNotNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.entity.MeasurementLocationEntity;
import com.oak.entity.UnitTypeEntity;
import com.oak.enums.MeasurementType;

public class ElementDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Element Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Element Id")
	private Long id;

	@IsNotBlank("Element Name")
	private String name;

	@IsNotBlank("Element Description")
	private String description;
	
	@ObjectAndPropertyNotNull(name = "Measurement Location", property = "id")
	@IdentifiableExists(MeasurementLocationEntity.class)
	private MeasurementLocationDto measurementLocation;

	@PropertyNotNull(name = "Unit Type", property = "id")
	@IdentifiableExists(UnitTypeEntity.class)
	private UnitTypeDto unitType;
	
	@IsNotNull("Measurement Type")
	private MeasurementType measurementType;
	
	private MeasurementBooleanOptionDto booleanOption;
	
	@NoNullElements("Text Options")
	private List<String> textOptions = new ArrayList<>();

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

	public MeasurementLocationDto getMeasurementLocation() {
		return measurementLocation;
	}

	public void setMeasurementLocation(MeasurementLocationDto measurementLocation) {
		this.measurementLocation = measurementLocation;
	}

	public UnitTypeDto getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitTypeDto unitType) {
		this.unitType = unitType;
	}

	public MeasurementType getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}

	public MeasurementBooleanOptionDto getBooleanOption() {
		return booleanOption;
	}

	public void setBooleanOption(MeasurementBooleanOptionDto booleanOption) {
		this.booleanOption = booleanOption;
	}

	public List<String> getTextOptions() {
		return textOptions;
	}

	public void setTextOptions(List<String> textOptions) {
		this.textOptions = textOptions;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description)
				.append("measurementLocation", measurementLocation).append("unitType", unitType)
				.append("measurementType", measurementType).append("booleanOption", booleanOption)
				.append("textOptions", textOptions).toString();
	}
}