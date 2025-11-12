/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.resources;

import com.granelia.dto.productDto;
import com.granelia.dto.ventaDto;
import com.granelia.service.productService;
import com.granelia.service.ventaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;



/**
 *
 * @author israel
 */
@Path("products")
public class productController {

    @Inject
    productService pSer;
    @Inject
    ventaService vSer;
    
@GET
@Path("catalogo")
@Produces(MediaType.APPLICATION_JSON)
public List<productDto> catalogoERP() {
     try{
        return pSer.catalogo();
     }catch(Exception e){
        return null;
     }
}

@POST
@Path("venta")
@Produces(MediaType.APPLICATION_JSON)
public String nuevaVenta(@Valid ventaDto req, @Context UriInfo uri) {
    
   return req.clienteId;
   
}

    

   
    
}
