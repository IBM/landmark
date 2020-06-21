package com.example.landmark.web;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class MuseumEndpointTest {

    @Test
    public void getByAddress() {
        given()
          .when().get("/api/museum/straat")
          .then()
             .statusCode(200)
             .body(notNullValue());
    }

}