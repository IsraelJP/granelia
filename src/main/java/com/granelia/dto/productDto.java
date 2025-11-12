/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.dto;

/**
 *
 * @author israel
 */
public class productDto {
      
    private int id_producto;
    private String nombre;
    private String marca;
    private String categoria;
    private int peso_gramos;
    private double precio;
    private double precio_iva;
    private int stock;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPeso_gramos() {
        return peso_gramos;
    }

    public void setPeso_gramos(int peso_gramos) {
        this.peso_gramos = peso_gramos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecio_iva() {
        return precio_iva;
    }

    public void setPrecio_iva(double precio_iva) {
        this.precio_iva = precio_iva;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
    
    
    
}
