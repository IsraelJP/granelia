/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.controller;

import com.granelia.dto.pagoDto;
import com.granelia.service.pagoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;

@Path("pagos")
public class pagoController {

    @Inject
    pagoService pSer;

    // GET /api/pagos/listar - Obtener todos los pagos
    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPagos() {
        try {
            List<pagoDto> pagos = pSer.listarPagos();
            return Response.ok(pagos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // GET /api/pagos/buscar/{transaccion} - Buscar por número de transacción
    @GET
    @Path("buscar/{transaccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorTransaccion(@PathParam("transaccion") String numTransaccion) {
        try {
            pagoDto pago = pSer.buscarPorTransaccion(numTransaccion);
            if (pago != null) {
                return Response.ok(pago).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Transacción no encontrada\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // GET /api/pagos/filtrar/{estado} - Filtrar por estado
    @GET
    @Path("filtrar/{estado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarPorEstado(@PathParam("estado") String estado) {
        try {
            List<pagoDto> pagos = pSer.filtrarPorEstado(estado);
            return Response.ok(pagos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // POST /api/pagos/registrar - Registrar nuevo pago (estado viene del banco)
    @POST
    @Path("registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarPago(@Valid pagoDto pago, @Context UriInfo uri) {
        try {
            pagoDto nuevo = pSer.registrarPago(pago);
            return Response.status(Response.Status.CREATED)
                    .entity(nuevo)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // DELETE /api/pagos/eliminar/{id} - Eliminar pago
    @DELETE
    @Path("eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarPago(@PathParam("id") int idPago) {
        try {
            boolean eliminado = pSer.eliminarPago(idPago);
            if (eliminado) {
                return Response.ok("{\"mensaje\": \"Pago eliminado correctamente\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"No se encontró el pago\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
