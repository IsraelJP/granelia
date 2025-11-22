/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class pagoDto {
    
    private int id_pago;
    private String numero_transaccion;
    private double monto;
    private LocalDate fecha_pago;
    private String titular;
    private String estado; // PENDIENTE, VALIDADO, RECHAZADO (viene del banco)
    private LocalDateTime fecha_registro;

    // Constructores
    public pagoDto() {}

    public pagoDto(String numero_transaccion, double monto, LocalDate fecha_pago, String titular) {
        this.numero_transaccion = numero_transaccion;
        this.monto = monto;
        this.fecha_pago = fecha_pago;
        this.titular = titular;
    }

    // Getters y Setters
    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public String getNumero_transaccion() {
        return numero_transaccion;
    }

    public void setNumero_transaccion(String numero_transaccion) {
        this.numero_transaccion = numero_transaccion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(LocalDate fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDateTime fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}