package com.oak.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.oak.dto.UserDetailsDto;
import com.oak.entity.PrivilegeEntity;
import com.oak.entity.RoleEntity;
import com.oak.entity.UserEntity;
import com.oak.repository.UserRepository;

@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new UserDetailsDto(user.getUsername(), user.getPassword(), user.isActive(),
				this.extractAuthorities(user));
	}

	private Collection<? extends GrantedAuthority> extractAuthorities(UserEntity user) {
		return user.getRoles().stream().map(RoleEntity::getPrivileges).flatMap(Set::stream)
				.map(PrivilegeEntity::getName).map(SimpleGrantedAuthority::new).collect(toList());
	}
}
