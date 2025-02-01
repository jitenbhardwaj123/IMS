package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;

@Entity
@Table(name = "upload_record")
public class UploadRecordEntity extends Auditable implements Mergeable<UploadRecordEntity, Long> {
	@Id
	@SequenceGenerator(name = "upload_record_seq", sequenceName = "upload_record_seq", allocationSize = 1)
	@GeneratedValue(generator = "upload_record_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@ElementCollection
	@CollectionTable(name = "upload_record_file_resource", joinColumns = @JoinColumn(name = "upload_record_id", referencedColumnName = "id"))
	@Column(name = "file_resource_id")
	@NotEmptyAndNoNullElements("File Resource Ids")
	private Set<Long> fileResourceIds = new HashSet<>();

	private UploadRecordEntity(UploadRecordEntityBuilder uploadRecordEntityBuilder) {
		this.id = uploadRecordEntityBuilder.id;
		this.setFileResourceIds(uploadRecordEntityBuilder.fileResourceIds);
	}

	public UploadRecordEntity() {
	}

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
		this.fileResourceIds.clear();
		if (isNotEmpty(fileResourceIds)) {
			fileResourceIds.forEach(this::addFileResourceId);
		}
	}

	public void addFileResourceId(Long fileResourceId) {
		this.fileResourceIds.add(fileResourceId);
	}
	
	@Override
	public UploadRecordEntity merge(UploadRecordEntity other) {
		if (other != null) {
			this.setFileResourceIds(other.fileResourceIds);
		}
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("fileResourceIds", fileResourceIds).toString();
	}

	public static UploadRecordEntityBuilder builder() {
		return new UploadRecordEntityBuilder();
	}

	public static UploadRecordEntityBuilder builderFrom(UploadRecordEntity uploadRecordEntity) {
		return new UploadRecordEntityBuilder(uploadRecordEntity);
	}

	public static final class UploadRecordEntityBuilder {
		private Long id;
		private Set<Long> fileResourceIds = Collections.emptySet();

		private UploadRecordEntityBuilder() {
		}

		private UploadRecordEntityBuilder(UploadRecordEntity uploadRecordEntity) {
			this.id = uploadRecordEntity.id;
			this.fileResourceIds = uploadRecordEntity.fileResourceIds;
		}

		public UploadRecordEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public UploadRecordEntityBuilder fileResourceIds(Set<Long> fileResourceIds) {
			this.fileResourceIds = fileResourceIds;
			return this;
		}

		public UploadRecordEntity build() {
			return new UploadRecordEntity(this);
		}
	}
}