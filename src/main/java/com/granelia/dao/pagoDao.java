/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.dao;

import com.granelia.dto.pagoDto;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.sql.DataSource;

@ApplicationScoped
public class pagoDao {

    @Resource(lookup = "jdbc/granelia")
    private DataSource ds;

    // Listar todos los pagos
    public List<pagoDto> listarPagos() {
        List<pagoDto> list = new ArrayList<>();
        String sql = "SELECT * FROM PAGOS_GRANELIA ORDER BY fecha_registro DESC";

        try (Connection cn = ds.getConnection();
             Statement ps = cn.createStatement();
             ResultSet rs = ps.executeQuery(sql)) {

            while (rs.next()) {
                pagoDto dto = mapearPago(rs);
                list.add(dto);
            }
            return list;
        } catch (SQLException e) {
            System.err.println("❌ Error al listar pagos: " + e.getMessage());
            return null;
        }
    }

    // Insertar nuevo pago (el estado se simula automáticamente del "banco")
    @Transactional
    public pagoDto insertarPago(pagoDto pago) throws SQLException {
        String sql = "INSERT INTO PAGOS_GRANELIA (numero_transaccion, monto, fecha_pago, titular, estado) VALUES (?, ?, ?, ?, ?)";

        // Simular respuesta del banco (aleatorio para demo)
        String estadoBanco = simularRespuestaBanco();

        try (Connection cn = ds.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pago.getNumero_transaccion());
            ps.setDouble(2, pago.getMonto());
            ps.setDate(3, Date.valueOf(pago.getFecha_pago()));
            ps.setString(4, pago.getTitular());
            ps.setString(5, estadoBanco);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        pago.setId_pago(keys.getInt(1));
                    }
                }
                pago.setEstado(estadoBanco);
                System.out.println("✅ Pago insertado: " + pago.getNumero_transaccion() + " - Estado: " + estadoBanco);
            }
            return pago;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar pago: " + e.getMessage());
            throw e;
        }
    }

    // Simula la respuesta del banco (70% validado, 20% pendiente, 10% rechazado)
    private String simularRespuestaBanco() {
        Random rand = new Random();
        int num = rand.nextInt(100);
        if (num < 70) {
            return "VALIDADO";
        } else if (num < 90) {
            return "PENDIENTE";
        } else {
            return "RECHAZADO";
        }
    }

    // Buscar pago por número de transacción
    public pagoDto buscarPorTransaccion(String numTransaccion) throws SQLException {
        String sql = "SELECT * FROM PAGOS_GRANELIA WHERE numero_transaccion = ?";

        try (Connection cn = ds.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, numTransaccion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPago(rs);
                }
            }
        }
        return null;
    }

    // Buscar pago por ID
    public pagoDto buscarPorId(int idPago) throws SQLException {
        String sql = "SELECT * FROM PAGOS_GRANELIA WHERE id_pago = ?";

        try (Connection cn = ds.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idPago);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPago(rs);
                }
            }
        }
        return null;
    }

    // Eliminar pago
    @Transactional
    public boolean eliminarPago(int idPago) throws SQLException {
        String sql = "DELETE FROM PAGOS_GRANELIA WHERE id_pago = ?";

        try (Connection cn = ds.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idPago);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Pago eliminado ID: " + idPago);
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar: " + e.getMessage());
            throw e;
        }
    }

    // Listar pagos por estado
    public List<pagoDto> listarPorEstado(String estado) {
        List<pagoDto> list = new ArrayList<>();
        String sql = "SELECT * FROM PAGOS_GRANELIA WHERE estado = ? ORDER BY fecha_registro DESC";

        try (Connection cn = ds.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, estado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapearPago(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            System.err.println("❌ Error al filtrar por estado: " + e.getMessage());
            return null;
        }
    }

    // Método auxiliar para mapear ResultSet a DTO
    private pagoDto mapearPago(ResultSet rs) throws SQLException {
        pagoDto dto = new pagoDto();
        dto.setId_pago(rs.getInt("id_pago"));
        dto.setNumero_transaccion(rs.getString("numero_transaccion"));
        dto.setMonto(rs.getDouble("monto"));
        dto.setFecha_pago(rs.getDate("fecha_pago").toLocalDate());
        dto.setTitular(rs.getString("titular"));
        dto.setEstado(rs.getString("estado"));
        Timestamp ts = rs.getTimestamp("fecha_registro");
        if (ts != null) {
            dto.setFecha_registro(ts.toLocalDateTime());
        }
        return dto;
    }
}