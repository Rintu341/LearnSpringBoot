package com.sujan.controller;

import jakarta.validation.constraints.Past;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
public class UserController {
    @GET
    @Path("details")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDetails(){
        return "Hello World!";
    }

}
