package com.oak.dto;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;

public class RoleDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Role Id")
	@IsNotNull(groups = UpdateValidationGroup.class, value = "Role Id")
	private Long id;

	@IsNotBlank("Role Name")
	private String name;
	
	@NotEmptyAndNoNullElements("Role Privileges")
	private final Set<PrivilegeDto> privileges = new HashSet<>();

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

	public Set<PrivilegeDto> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<PrivilegeDto> privileges) {
		this.privileges.clear();
		if (isNotEmpty(privileges)) {
			privileges.forEach(this::addPrivilege);
		}
	}

	public void addPrivilege(PrivilegeDto privilege) {
		this.privileges.add(privilege);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("privileges", privileges).toString();
	}
}