package com.oak.repository;

import java.util.Optional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.entity.RoleEntity;

public interface RoleRepository extends CommonCustomJpaRepository<RoleEntity, Long> {
	Optional<RoleEntity> findByName(String name);
}
