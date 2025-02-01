package com.oak.configuration;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("h2") 
public class H2ServerConfig {
 
    @Value("${h2.tcp.port:9092}")
    private String h2TcpPort;

    /**
     * TCP connection to enable SQL clients to connect with embedded h2 database. 
     * For example, connecting from JMeter use the database URL: jdbc:h2:tcp://localhost:9091/mem:testdb
     */
    @Bean
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
    }
}