/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.service;

import com.granelia.dao.pagoDao;
import com.granelia.dto.pagoDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class pagoService {

    @Inject
    pagoDao dao;

    // Listar todos los pagos
    public List<pagoDto> listarPagos() {
        return dao.listarPagos();
    }

    // Registrar nuevo pago (el estado viene del banco automáticamente)
    public pagoDto registrarPago(pagoDto pago) throws SQLException {
        // Validaciones
        if (pago.getNumero_transaccion() == null || pago.getNumero_transaccion().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de transacción es obligatorio");
        }
        if (pago.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        if (pago.getFecha_pago() == null) {
            throw new IllegalArgumentException("La fecha del pago es obligatoria");
        }
        if (pago.getTitular() == null || pago.getTitular().trim().isEmpty()) {
            throw new IllegalArgumentException("El titular es obligatorio");
        }

        // Verificar que no exista ya esa transacción
        pagoDto existente = dao.buscarPorTransaccion(pago.getNumero_transaccion());
        if (existente != null) {
            throw new IllegalArgumentException("Ya existe un pago con ese número de transacción");
        }

        return dao.insertarPago(pago);
    }

    // Buscar por número de transacción
    public pagoDto buscarPorTransaccion(String numTransaccion) throws SQLException {
        if (numTransaccion == null || numTransaccion.trim().isEmpty()) {
            return null;
        }
        return dao.buscarPorTransaccion(numTransaccion.trim());
    }

    // Filtrar por estado
    public List<pagoDto> filtrarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty() || "TODOS".equals(estado)) {
            return listarPagos();
        }
        return dao.listarPorEstado(estado);
    }

    // Eliminar pago
    public boolean eliminarPago(int idPago) throws SQLException {
        return dao.eliminarPago(idPago);
    }
}