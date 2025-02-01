package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;

@Entity
@Table(name = "role")
public class RoleEntity extends Auditable implements Mergeable<RoleEntity, Long> {
	@Id
	@SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
	@GeneratedValue(generator = "role_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Role Name")
	private String name;

	@OneToMany
	@JoinTable(name = "role_privilege", joinColumns = {
			@JoinColumn(name = "role_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "privilege_id") })
	@NotEmptyAndNoNullElements("Role Privileges")
	private final Set<PrivilegeEntity> privileges = new HashSet<>();

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

	public Set<PrivilegeEntity> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<PrivilegeEntity> privileges) {
		this.privileges.clear();
		if (isNotEmpty(privileges)) {
			privileges.forEach(this::addPrivilege);
		}
	}

	public void addPrivilege(PrivilegeEntity privilege) {
		this.privileges.add(privilege);
	}

	@Override
	public RoleEntity merge(RoleEntity other) {
		if (other != null) {
			this.name = other.name;
			this.setPrivileges(other.privileges);
		}
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("privileges", privileges).toString();
	}
}
