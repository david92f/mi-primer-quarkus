package com.david.testresources;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;
import java.util.HashMap;

public class PostgreSQLTestResource implements QuarkusTestResourceLifecycleManager {

    private PostgreSQLContainer<?> postgreSQLContainer;

    @Override
    public Map<String, String> start() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine")
                .withDatabaseName("quarkus")
                .withUsername("quarkus")
                .withPassword("quarkus");
        
        postgreSQLContainer.start();
        
        Map<String, String> config = new HashMap<>();
        config.put("quarkus.datasource.jdbc.url", postgreSQLContainer.getJdbcUrl());
        config.put("quarkus.datasource.username", postgreSQLContainer.getUsername());
        config.put("quarkus.datasource.password", postgreSQLContainer.getPassword());
        
        return config;
    }

    @Override
    public void stop() {
        if (postgreSQLContainer != null && postgreSQLContainer.isRunning()) {
            postgreSQLContainer.stop();
        }
    }
}
