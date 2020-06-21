package com.example.landmark.service;

import com.example.landmark.model.Museum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MuseumService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumService.class);

    private Map<Integer, Museum> museums;

    @Inject
    MuseumRepository repository;

    @PostConstruct
    private void init() {
         LOGGER.info("Starting the initialization of museum locations...");
         this.museums = new HashMap<>();
         this.museums.put(0, new Museum("Default", "Default", "Default", "Default"));
         LOGGER.info("{} museums were detected", museums.size());
    }

    public Museum getByAddress(String address) {
        return repository.findByAddress(address).orElse(museums.get(0));
    }
}


