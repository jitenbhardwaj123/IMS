package com.oak.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;

public class SourceDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Source Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Source Id")
	private Long id;

	@IsNotBlank("Source Name")
	private String name;

	@IsNotBlank("Source Description")
	private String description;
	
	private boolean manualSource;
	private boolean tagBased;
	private boolean editable;

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

	public boolean isManualSource() {
		return manualSource;
	}

	public void setManualSource(boolean manualSource) {
		this.manualSource = manualSource;
	}

	public boolean isTagBased() {
		return tagBased;
	}

	public void setTagBased(boolean tagBased) {
		this.tagBased = tagBased;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).append("manualSource", manualSource)
				.append("tagBased", tagBased).append("editable", editable).toString();
	}

}