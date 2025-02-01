package com.oak.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
public class EncodingConfig {

    private final ObjectMapper objectMapper;

    @Autowired
    public EncodingConfig(
            @Qualifier("feignObjectMapper")
                    ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public Encoder encoder() {
        return new JacksonEncoder(objectMapper);
    }

    @Bean
    public Decoder decoder() {
        return new JacksonDecoder(objectMapper);
    }

}
