/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.resources;

import com.granelia.dto.productDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;



/**
 *
 * @author israel
 */
@Path("products")
public class productController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public productDto crearProducto(
            @QueryParam("idProduct") String idProduct,
            @QueryParam("nameProduct") String nameProduct,
            @QueryParam("price") String price,
            @QueryParam("typeProduct") String typeProduct,
            @QueryParam("expDate") String expDate
    ){
     productDto prN = new productDto();
     prN.setIdProduct(idProduct);
     prN.setNameProduct(nameProduct);
     prN.setPrice(price);
     prN.setTypeProduct(typeProduct);
     prN.setExpDate(expDate);
     prN.setOk("ok");
     return prN;
    }
    
}
