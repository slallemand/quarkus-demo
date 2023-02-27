package com.redhat.developers;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;


@Path("/hello")
public class GreetingResource {

    private static final Logger LOG = Logger.getLogger(GreetingResource.class);

    @Inject
    MeterRegistry registry;

    @ConfigProperty(name = "greeting")
    String greeting;

    private String HOSTNAME =
       System.getenv().getOrDefault("HOSTNAME", "unknown");
    
    private int count = 0;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        count++;
        LOG.info("/hello called");
        registry.counter("greeting_counter").increment();
        return greeting + " " + HOSTNAME + ":" + count + "\n";
    }

}