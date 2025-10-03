/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.resources;

import com.granelia.dto.userDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author israel
 */
@Path("users")
public class userController {                       
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public userDto crearUsuario(
            @QueryParam("username") String username,
            @QueryParam("email") String email,
            @QueryParam("phone") String phone,
            @QueryParam("nameCompany") String nameCompany,
            @QueryParam("contactName") String contactName
    ) {
        userDto usN = new userDto();
        usN.setUsername(username);
        usN.setEmail(email);
        usN.setPhone(phone);
        usN.setNameCompany(nameCompany);
        usN.setContactName(contactName);
        usN.setOk("ok");
        return usN;
    }
}
    

