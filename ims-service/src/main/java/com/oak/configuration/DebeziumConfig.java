package com.oak.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConfig {
	
	public class DebeziumProperties { 
		private Map<String, String> config;

		public Map<String, String> getConfig() {
			return config;
		}

		public void setConfig(Map<String, String> config) {
			this.config = config;
		} 
	}
	
    @Bean
    @ConfigurationProperties(prefix = "debezium")
    public DebeziumProperties dbzProperties() {
        return new DebeziumProperties();
    }
    
	@Bean
	public io.debezium.config.Configuration dbzConfig(DebeziumProperties dbzProperties) {
	    return io.debezium.config.Configuration.from(dbzProperties.getConfig());
	}
}
