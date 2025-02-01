package com.oak.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsDto implements UserDetails {
	private static final long serialVersionUID = 4195348377329917765L;
	
	private final String username;
	private final String password;
	private final boolean active;
	private final Collection<? extends GrantedAuthority> authorities;

	public UserDetailsDto(String username, String password, boolean active,
			Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.active = active;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.active;
	}

	@Override
	public boolean isEnabled() {
		return this.active;
	}
}
