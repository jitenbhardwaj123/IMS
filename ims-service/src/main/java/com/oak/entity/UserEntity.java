package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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

@Entity
@Table(name = "user")
public class UserEntity extends Auditable implements Mergeable<UserEntity, Long> {
	@Id
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	@GeneratedValue(generator = "user_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	@IsNotBlank("Username")
	private String username;
	
	@Column(name = "password")
	@IsNotBlank("Password")
	private String password;
	
	@Column(name = "first_name")
	@IsNotBlank("First Name")
	private String firstName;
	
	@Column(name = "last_name")
	@IsNotBlank("Last Name")
	private String lastName;
	
	@Column(name = "email")
	@IsNotBlank("Email")
	private String email;
	
	@Column(name = "active")
	private boolean active;

	@OneToMany
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id") })
	private Set<RoleEntity> roles = new HashSet<>();

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

	public String getPassword() {
		return password;
	}

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

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles.clear();
		if (isNotEmpty(roles)) {
			roles.forEach(this::addRole);
		}
	}

	public void addRole(RoleEntity role) {
		this.roles.add(role);
	}

	@Override
	public UserEntity merge(UserEntity other) {
		if (other != null) {
			this.username = other.username;
			if (isNotBlank(other.password)) {
				this.password = other.password;
			}
			this.firstName = other.firstName;
			this.lastName = other.lastName;
			this.email = other.email;
			this.active = other.active;
			this.setRoles(other.roles);
		}
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("username", username).append("firstName", firstName).append("lastName", lastName)
				.append("email", email).append("active", active).append("roles", roles).toString();
	}
}
