package com.oak.dto;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;

public class UserDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "User Id")
	@IsNotNull(groups = UpdateValidationGroup.class, value = "User Id")
	private Long id;

	@IsNotBlank("Username")
	private String username;

	@JsonIgnore
	private String password;

	@IsNotBlank("First Name")
	private String firstName;

	@IsNotBlank("Last Name")
	private String lastName;

	@IsNotBlank("Email")
	private String email;

	private boolean active;

	private final Set<RoleDto> roles = new HashSet<>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDto> roles) {
		this.roles.clear();
		if (isNotEmpty(roles)) {
			roles.forEach(this::addRole);
		}
	}

	public void addRole(RoleDto role) {
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("username", username).append("firstName", firstName).append("lastName", lastName)
				.append("email", email).append("active", active).append("roles", roles).toString();
	}
}
