package com.oak.repository;

import java.util.List;
import java.util.Optional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.entity.UnitEntity;

public interface UnitRepository extends CommonCustomJpaRepository<UnitEntity, Long> {
	List<UnitEntity> findByUnitTypeId(long id);
	Optional<UnitEntity> findByName(String name);
}
