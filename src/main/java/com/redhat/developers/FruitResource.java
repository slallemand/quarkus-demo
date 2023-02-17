package com.redhat.developers;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/fruit")
public class FruitResource {



    @RestClient
    FruityViceService fruityViceService;

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newFruit(Fruit fruit) {
        fruit.id = null;
        fruit.persist();
        return Response.status(Status.CREATED).entity(fruit).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FruitDTO> fruits(@QueryParam("season") String season) {
        if (season != null) {
            return Fruit.findBySeason(season).stream()
                    .map(fruit -> FruitDTO.of(fruit, fruityViceService.getFruitByName(fruit.name)))
                    .collect(Collectors.toList());
        }
        return Fruit.<Fruit>listAll().stream()
                .map(fruit -> FruitDTO.of(fruit, fruityViceService.getFruitByName(fruit.name)))
                .collect(Collectors.toList());
    }

}