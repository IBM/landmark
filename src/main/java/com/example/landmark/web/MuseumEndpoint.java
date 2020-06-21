package com.example.landmark.web;

import com.example.landmark.model.Museum;
import com.example.landmark.service.MuseumService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

@Path("museum")
@RequestScoped
public class MuseumEndpoint {
    private static final int MIN = 2_500;
    private static final int MAX = 10_000;
    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumEndpoint.class);

    @Inject
    @ConfigProperty(name = "app.random.enabled", defaultValue = "false")
    Boolean randomResponseEnabled;

    @Inject
    private MuseumService museumService;

    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @Retry(maxRetries = 2, maxDuration = 2000)
    @Fallback(fallbackMethod = "fallbackService")
    @GET
    @Path("/{address}")
    @Produces(MediaType.APPLICATION_JSON)
      public Museum getByAddress(@PathParam("address") String address) throws InterruptedException {
        if (randomResponseEnabled) {
            int random = ThreadLocalRandom.current().nextInt(MIN, MAX);
            LOGGER.info("Museum service will respond in {} ms", random);
            Thread.sleep(random);
        }
        return this.museumService.getByAddress(address);
    }


    public Museum fallbackService(String address) {
        Museum museumNotFound = new Museum();
        museumNotFound.setMessage(String.format("A landmark for address %s could not be found.", address));
        return museumNotFound;
    }
}
