package com.oak.repository;

import java.util.Optional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.entity.PrivilegeEntity;

public interface PrivilegeRepository extends CommonCustomJpaRepository<PrivilegeEntity, Long> {
	Optional<PrivilegeEntity> findByName(String name);
}
