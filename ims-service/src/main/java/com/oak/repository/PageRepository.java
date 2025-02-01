package com.oak.repository;

import java.util.List;
import java.util.Optional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.entity.PageEntity;

public interface PageRepository extends CommonCustomJpaRepository<PageEntity, Long> {
	List<PageEntity> findByParentNull();
	List<PageEntity> findByParentNullAndAssetTypesIdIn(List<Long> assetTypes);
	Optional<PageEntity> findByDashboardTrueAndAssetTypesId(Long assetType);
	List<PageEntity> findByParentNullAndDashboardTrue();
	List<PageEntity> findByName(String name);
	List<PageEntity> findByWidgetsId(long widgetId);
}
