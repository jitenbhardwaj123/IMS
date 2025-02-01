package com.oak.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;

public class FileResourceDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "File Resource Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "File Resource Id")
	private Long id;
	
	@JsonIgnore
	private byte[] rawData;

	@IsNotBlank("File Name")
	private String fileName;

	@IsNotBlank("File Extension")
	private String fileExtension;

	@IsNotNull("File Size")
	private Long fileSize;
	
	private String httpContentType;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getRawData() {
		return rawData;
	}

	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

    @JsonIgnore
    public String getFileNameWithExtension() {
        return this.fileName + "." + this.fileExtension;
    }

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getHttpContentType() {
		return httpContentType;
	}

	public void setHttpContentType(String httpContentType) {
		this.httpContentType = httpContentType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("fileName", fileName).append("fileExtension", fileExtension)
				.append("fileSize", fileSize).append("httpContentType", httpContentType).toString();
	}

}