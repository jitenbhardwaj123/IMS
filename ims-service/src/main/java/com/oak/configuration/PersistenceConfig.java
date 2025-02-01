package com.oak.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.oak.common.orm.repository.CommonCustomJpaRepositoryImpl;
import com.oak.repository.RepositoryPackageScanner;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(repositoryBaseClass = CommonCustomJpaRepositoryImpl.class, basePackageClasses = {
		RepositoryPackageScanner.class })
public class PersistenceConfig {
}
