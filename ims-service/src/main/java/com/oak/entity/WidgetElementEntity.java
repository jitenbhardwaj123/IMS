package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;

@Entity
@Table(name = "widget_element")
public class WidgetElementEntity implements Identifiable {
	@Id
	@SequenceGenerator(name = "widget_element_seq", sequenceName = "widget_element_seq", allocationSize = 1)
	@GeneratedValue(generator = "widget_element_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@JoinColumn(name = "element_id")
	@OneToOne
	@IsNotNull("Element")
	private ElementEntity element;

	@Column(name = "name")
	@IsNotBlank("Element Display Name")
	private String name;

	@Column(name = "display_format")
	private String displayFormat;

	@Column(name = "creatable")
	private boolean creatable;
	
	@Column(name = "editable")
	private boolean editable;
	
	@Column(name = "mandatory")
	private boolean mandatory;
	
	@Column(name = "deletable")
	private boolean deletable;
	
	@Column(name = "importable")
	private boolean importable;
	
	@Column(name = "graphable")
	private boolean graphable;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public ElementEntity getElement() {
		return element;
	}

	public void setElement(ElementEntity element) {
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
