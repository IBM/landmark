package com.example.landmark.health;

import com.example.landmark.service.MuseumRepository;
import com.example.landmark.service.MuseumService;
import io.quarkus.launcher.shaded.com.google.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class DatabaseConnectionHealthCheck implements HealthCheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionHealthCheck.class);

    @ConfigProperty(name = "database.up", defaultValue = "true")
    private boolean databaseUp;

    @Override
    public HealthCheckResponse call() {

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Database connection health check");

        try {
            simulateDatabaseConnectionVerification();
            responseBuilder.up();
        } catch (IllegalStateException e) {
            // cannot access the database
            responseBuilder.down();
        }

        return responseBuilder.build();
    }

    private void simulateDatabaseConnectionVerification() {
        if (!databaseUp) {
            throw new IllegalStateException("Could not count the database objects");
        }
    }
}