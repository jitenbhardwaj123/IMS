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

public class UploadRecordDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Upload Record Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Upload Record Id")
	private Long id;
	
	@NotEmptyAndNoNullElements("File Resource Ids")
	private Set<Long> fileResourceIds = new HashSet<>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Set<Long> getFileResourceIds() {
		return fileResourceIds;
	}

	public void setFileResourceIds(Set<Long> fileResourceIds) {
		this.fileResourceIds = fileResourceIds;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("fileResourceIds", fileResourceIds).toString();
	}

}