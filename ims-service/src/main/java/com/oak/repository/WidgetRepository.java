package com.oak.repository;

import java.util.List;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.entity.WidgetEntity;

public interface WidgetRepository extends CommonCustomJpaRepository<WidgetEntity, Long> {
	List<WidgetEntity> findByAssetTypesIdIn(List<Long> assetTypes);
}
