package com.oak.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotEmpty;
import com.oak.common.validation.constraint.IsNotNull;

@Entity
@Table(name = "file_resource")
public class FileResourceEntity extends Auditable implements Mergeable<FileResourceEntity, Long> {
	@Id
	@SequenceGenerator(name = "file_resource_seq", sequenceName = "file_resource_seq", allocationSize = 1)
	@GeneratedValue(generator = "file_resource_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Basic(fetch = LAZY)
	@Column(name = "raw_data")
	@IsNotEmpty("File Data")
	private byte[] rawData;

	@Column(name = "file_name")
	@IsNotBlank("File Name")
	private String fileName;

	@Column(name = "file_extension")
	@IsNotBlank("File Extension")
	private String fileExtension;

	@Column(name = "file_size")
	@IsNotNull("File Size")
	private Long fileSize;

	@Column(name = "http_content_type")
	private String httpContentType;

	private FileResourceEntity(FileResourceEntityBuilder fileResourceEntityBuilder) {
		this.id = fileResourceEntityBuilder.id;
		this.rawData = fileResourceEntityBuilder.rawData;
		this.fileName = fileResourceEntityBuilder.fileName;
		this.fileExtension = fileResourceEntityBuilder.fileExtension;
		this.fileSize = fileResourceEntityBuilder.fileSize;
		this.httpContentType = fileResourceEntityBuilder.httpContentType;
	}

	public FileResourceEntity() {
	}

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
	public FileResourceEntity merge(FileResourceEntity other) {
		if (other != null) {
			this.setRawData(other.getRawData());
			this.setFileName(other.getFileName());
			this.setFileExtension(other.getFileExtension());
			this.setFileSize(other.getFileSize());
			this.setHttpContentType(other.getHttpContentType());
		}
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("fileName", fileName).append("fileExtension", fileExtension).append("fileSize", fileSize)
				.append("httpContentType", httpContentType).toString();
	}

	public static FileResourceEntityBuilder builder() {
		return new FileResourceEntityBuilder();
	}

	public static FileResourceEntityBuilder builderFrom(FileResourceEntity fileResourceEntity) {
		return new FileResourceEntityBuilder(fileResourceEntity);
	}

	public static final class FileResourceEntityBuilder {
		private Long id;
		private byte[] rawData;
		private String fileName;
		private String fileExtension;
		private Long fileSize;
		private String httpContentType;

		private FileResourceEntityBuilder() {
		}

		private FileResourceEntityBuilder(FileResourceEntity fileResourceEntity) {
			this.id = fileResourceEntity.id;
			this.rawData = fileResourceEntity.rawData;
			this.fileName = fileResourceEntity.fileName;
			this.fileExtension = fileResourceEntity.fileExtension;
			this.fileSize = fileResourceEntity.fileSize;
			this.httpContentType = fileResourceEntity.httpContentType;
		}

		public FileResourceEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public FileResourceEntityBuilder rawData(byte[] rawData) {
			this.rawData = rawData;
			return this;
		}

		public FileResourceEntityBuilder fileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public FileResourceEntityBuilder fileExtension(String fileExtension) {
			this.fileExtension = fileExtension;
			return this;
		}

		public FileResourceEntityBuilder fileSize(Long fileSize) {
			this.fileSize = fileSize;
			return this;
		}

		public FileResourceEntityBuilder httpContentType(String httpContentType) {
			this.httpContentType = httpContentType;
			return this;
		}

		public FileResourceEntity build() {
			return new FileResourceEntity(this);
		}
	}
}