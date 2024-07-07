package com.kyljmeeski.vacanciesbot.telegrambot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    Properties properties = new Properties();
    private final String mode;

    public ApplicationProperties() {
        try {
            InputStream inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            if (properties.getProperty("mode") != null && properties.getProperty("mode").equals("component")) {
                mode = "component";
            } else {
                mode = "standalone";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String rabbitMQHost() {
        if (mode.equals("component")) {
            return System.getenv("RABBITMQ_HOST");
        }
        return properties.getProperty("rabbitmq.host");
    }

    public int rabbitMQPort() {
        if (mode.equals("component")) {
            return Integer.parseInt(System.getenv("RABBITMQ_PORT"));
        }
        return Integer.parseInt(properties.getProperty("rabbitmq.port"));
    }

    public String postgresHost() {
        if (mode.equals("component")) {
            return System.getenv("POSTGRES_HOST");
        }
        return properties.getProperty("postgres.host");
    }

    public int postgresPort() {
        if (mode.equals("component")) {
            return Integer.parseInt(System.getenv("POSTGRES_PORT"));
        }
        return Integer.parseInt(properties.getProperty("postgres.port"));
    }

    public String postgresDB() {
        if (mode.equals("component")) {
            return System.getenv("POSTGRES_DB");
        }
        return properties.getProperty("postgres.db");
    }

    public String postgresName() {
        if (mode.equals("component")) {
            return System.getenv("POSTGRES_NAME");
        }
        return properties.getProperty("postgres.name");
    }

    public String postgresPassword() {
        if (mode.equals("component")) {
            return System.getenv("POSTGRES_PASSWORD");
        }
        return properties.getProperty("postgres.password");
    }

}
