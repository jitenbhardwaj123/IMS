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
@Table(name = "link_record")
public class LinkRecordEntity extends Auditable implements Mergeable<LinkRecordEntity, Long> {
	@Id
	@SequenceGenerator(name = "link_record_seq", sequenceName = "link_record_seq", allocationSize = 1)
	@GeneratedValue(generator = "link_record_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@ElementCollection
	@CollectionTable(name = "link_record_link", joinColumns = @JoinColumn(name = "link_record_id", referencedColumnName = "id"))
	@Column(name = "http_link")
	@NotEmptyAndNoNullElements("Http Links")
	private Set<String> httpLinks = new HashSet<>();

	private LinkRecordEntity(LinkRecordEntityBuilder linkRecordEntityBuilder) {
		this.id = linkRecordEntityBuilder.id;
		this.setHttpLinks(linkRecordEntityBuilder.httpLinks);
	}

	public LinkRecordEntity() {
	}

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
		this.httpLinks.clear();
		if (isNotEmpty(httpLinks)) {
			httpLinks.forEach(this::addHttpLink);
		}
	}

	public void addHttpLink(String httpLink) {
		this.httpLinks.add(httpLink);
	}
	
	@Override
	public LinkRecordEntity merge(LinkRecordEntity other) {
		if (other != null) {
			this.setHttpLinks(other.httpLinks);
		}
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("httpLinks", httpLinks).toString();
	}

	public static LinkRecordEntityBuilder builder() {
		return new LinkRecordEntityBuilder();
	}

	public static LinkRecordEntityBuilder builderFrom(LinkRecordEntity linkRecordEntity) {
		return new LinkRecordEntityBuilder(linkRecordEntity);
	}

	public static final class LinkRecordEntityBuilder {
		private Long id;
		private Set<String> httpLinks = Collections.emptySet();

		private LinkRecordEntityBuilder() {
		}

		private LinkRecordEntityBuilder(LinkRecordEntity linkRecordEntity) {
			this.id = linkRecordEntity.id;
			this.httpLinks = linkRecordEntity.httpLinks;
		}

		public LinkRecordEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public LinkRecordEntityBuilder httpLinks(Set<String> httpLinks) {
			this.httpLinks = httpLinks;
			return this;
		}

		public LinkRecordEntity build() {
			return new LinkRecordEntity(this);
		}
	}
}