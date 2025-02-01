package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.NoNullElements;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;

@Entity
@Table(name = "analytics_engine_template")
public class AnalyticsEngineTemplateEntity extends Auditable implements Mergeable<AnalyticsEngineTemplateEntity, Long> {
	@Id
	@SequenceGenerator(name = "analytics_engine_template_seq", sequenceName = "analytics_engine_template_seq", allocationSize = 1)
	@GeneratedValue(generator = "analytics_engine_template_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Template Name")
	private String name;

	@Column(name = "description")
	@IsNotBlank("Template Description")
	private String description;

	@Column(name = "template")
	@IsNotBlank("Template")
	private String template;

	@Column(name = "return_type")
	@IsNotBlank("Template Return Type")
	private String returnType;

	@ElementCollection
	@CollectionTable(name = "analytics_engine_template_import", joinColumns = @JoinColumn(name = "template_id"))
	@Column(name = "import")
	@OrderColumn(name = "import_order")
	@NoNullElements("Analytics Engine Template Imports")
	private List<String> imports = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "analytics_engine_template_exception", joinColumns = @JoinColumn(name = "template_id"))
	@Column(name = "exception")
	@OrderColumn(name = "exception_order")
	@NoNullElements("Analytics Engine Template Exceptions")
	private List<String> exceptions = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "analytics_engine_template_param", joinColumns = @JoinColumn(name = "template_id"))
	@Column(name = "param")
	@OrderColumn(name = "param_order")
	@NotEmptyAndNoNullElements("Analytics Engine Template Params")
	private List<String> params = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "analytics_engine_template_param_type", joinColumns = @JoinColumn(name = "template_id"))
	@Column(name = "param_type")
	@OrderColumn(name = "param_type_order")
	@NotEmptyAndNoNullElements("Analytics Engine Template Param Types")
	private List<String> paramTypes = new ArrayList<>();

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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<String> getImports() {
		return imports;
	}

	public void setImports(List<String> imports) {
		this.imports.clear();
		if (isNotEmpty(imports)) {
			this.imports.addAll(imports);
		}
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<String> exceptions) {
		this.exceptions.clear();
		if (isNotEmpty(exceptions)) {
			this.exceptions.addAll(exceptions);
		}
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params.clear();
		if (isNotEmpty(params)) {
			this.params.addAll(params);
		}
	}

	public List<String> getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(List<String> paramTypes) {
		this.paramTypes.clear();
		if (isNotEmpty(paramTypes)) {
			this.paramTypes.addAll(paramTypes);
		}
	}

	@Override
	public AnalyticsEngineTemplateEntity merge(AnalyticsEngineTemplateEntity other) {
		if (other != null) {
			this.name = other.name;
			this.description = other.description;
			this.template = other.template;
			this.returnType = other.returnType;
			this.setImports(other.imports);
			this.setExceptions(other.exceptions);
			this.setParams(other.params);
			this.setParamTypes(other.paramTypes);
		}
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).append("template", template)
				.append("returnType", returnType).append("imports", imports).append("exceptions", exceptions)
				.append("params", params).append("paramTypes", paramTypes).toString();
	}
}
