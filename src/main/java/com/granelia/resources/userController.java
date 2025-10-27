/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.resources;

import com.granelia.dto.userDto;
import com.granelia.service.userService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author israel
 */
@Path("users")
public class userController {                       

    @Inject userService service;
    
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public userDto crearUsuario(
            @QueryParam("username") String username,
            @QueryParam("email") String email,
            @QueryParam("phone") String phone,
            @QueryParam("nameCompany") String nameCompany,
            @QueryParam("contactName") String contactName,
            @QueryParam("password") String password
    ) {
        userDto usN = new userDto();
        usN.setUsername(username);
        usN.setEmail(email);
        usN.setPhone(phone);
        usN.setNameCompany(nameCompany);
        usN.setContactName(contactName);
        usN.setStatus("Activo");
        usN.setPassword(password);
        create(usN);
        return usN;
    }
    

    
    
        @POST
        public Response create(userDto body) {
            try {
                userDto saved = service.crearUsuario(body);
                return Response.status(Response.Status.CREATED).entity(saved).build();
            } catch (SQLException e) {
                return Response.serverError().entity(e.getMessage()).build();
            }
        }
        

        
}