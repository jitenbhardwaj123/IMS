package com.oak.repository;

import java.util.List;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.entity.AssetEntity;

public interface AssetRepository extends CommonCustomJpaRepository<AssetEntity, Long> {
	List<AssetEntity> findByParentNull();
	List<AssetEntity> findByName(String name);
}
