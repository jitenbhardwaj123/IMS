package com.oak.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.group.CreateValidationGroup;

public class WidgetElementDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Widget Element Id")
	private Long id;

	@IsNotNull("Element")
	private ElementDto element;

	@IsNotBlank("Element Display Name")
	private String name;

	private String displayFormat;
	private boolean creatable;
	private boolean editable;
	private boolean mandatory;
	private boolean deletable;
	private boolean importable;
	private boolean graphable;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public ElementDto getElement() {
		return element;
	}

	public void setElement(ElementDto element) {
		this.element = element;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayFormat() {
		return displayFormat;
	}

	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}

	public boolean isCreatable() {
		return creatable;
	}

	public void setCreatable(boolean creatable) {
		this.creatable = creatable;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public boolean isImportable() {
		return importable;
	}

	public void setImportable(boolean importable) {
		this.importable = importable;
	}

	public boolean isGraphable() {
		return graphable;
	}

	public void setGraphable(boolean graphable) {
		this.graphable = graphable;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("element", element).append("name", name).append("displayFormat", displayFormat)
				.append("creatable", creatable).append("editable", editable).append("mandatory", mandatory)
				.append("deletable", deletable).append("importable", importable).append("graphable", graphable)
				.toString();
	}
}
