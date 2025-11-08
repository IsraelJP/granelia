/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.service;

import com.granelia.dto.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author israel
 */
@ApplicationScoped
public class ApiService {

    private static final String URL_SERVICIO = "https://jsonplaceholder.typicode.com";
    private Client client;
    
    @PostConstruct
    void init() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .build();
    }
    
    void destroy(){
        if (client != null) {
            client.close();
        }
    }
    
        public List<User> listUsers() {
        try {
            Response r = client.target(URL_SERVICIO)
                    .path("/users")
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (r.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new RuntimeException("Error API: " + r.getStatus() + " - " + r.readEntity(String.class));
            }

            return r.readEntity(new GenericType<List<User>>() {});
        } catch (ProcessingException ex) {
            throw new RuntimeException("No se pudo contactar el API externo: " + ex.getMessage(), ex);
        }
    }

}
