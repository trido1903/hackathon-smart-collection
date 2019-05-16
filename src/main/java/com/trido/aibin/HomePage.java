package com.trido.aibin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("homepage")
public class HomePage {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Garbage demo(){
        return FetchData.getInstance().getGarbage();
    }

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String register(User user){
        return FetchData.getInstance().register(user);
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CustomizeJSON<Product> login(User user){
        return FetchData.getInstance().login(user);
    }

    @Path("/addproduct")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addProduct(Product product){
        if(FetchData.getInstance().addProduct(product)){
            System.out.println("Successfully");
        }else{
            System.out.println("Failed");
        }
    }
}
