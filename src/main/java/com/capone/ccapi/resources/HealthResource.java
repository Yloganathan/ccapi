package com.capone.ccapi.resources;

import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    public HealthResource(){
    }

    @GET
    @UnitOfWork
    public String checkHealth(){
        return "A OK";
    }
}
