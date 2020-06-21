package com.example.landmark.service;

import com.example.landmark.model.Museum;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class MuseumRepository implements PanacheRepository<Museum> {

    public Optional<Museum> findByAddress(String address) {
        return Optional.ofNullable(find("address like ?1",  "%"+address+"%").firstResult());
    }
}