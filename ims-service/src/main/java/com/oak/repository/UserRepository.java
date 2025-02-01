package com.oak.repository;

import java.util.Optional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.entity.UserEntity;

public interface UserRepository extends CommonCustomJpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
}
