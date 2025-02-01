package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotBlank;

@Entity
@Table(name = "privilege")
public class PrivilegeEntity implements Mergeable<PrivilegeEntity, Long> {
	@Id
	@SequenceGenerator(name = "privilege_seq", sequenceName = "privilege_seq", allocationSize = 1)
	@GeneratedValue(generator = "privilege_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Privilege Name")
	private String name;

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

	@Override
	public PrivilegeEntity merge(PrivilegeEntity other) {
		if (other != null) {
			this.name = other.name;
		}
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).toString();
	}
}
