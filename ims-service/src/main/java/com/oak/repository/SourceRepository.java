package com.oak.repository;

import java.util.Optional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.entity.SourceEntity;

public interface SourceRepository extends CommonCustomJpaRepository<SourceEntity, Long> {
	Optional<SourceEntity> findByName(String name);
}
