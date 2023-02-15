package com.redhat.developers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.eclipse.microprofile.config.inject.ConfigProperty;


@QuarkusTest
public class GreetingResourceTest {
    @ConfigProperty(name = "greeting")
    String greeting;


    private String HOSTNAME =
       System.getenv().getOrDefault("HOSTNAME", "unknown");
    
    private int count = 1;
    
    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is(greeting  + " " + HOSTNAME + ":" + count + "\n"));
    }

}