package com.oak.dto;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;

public class LinkRecordDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Link Record Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Link Record Id")
	private Long id;
	
	@NotEmptyAndNoNullElements("Http Links")
	private Set<String> httpLinks = new HashSet<>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Set<String> getHttpLinks() {
		return httpLinks;
	}

	public void setHttpLinks(Set<String> httpLinks) {
		this.httpLinks = httpLinks;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("httpLinks", httpLinks).toString();
	}
}