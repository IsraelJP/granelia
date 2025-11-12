/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.dto;

import java.util.List;

/**
 *
 * @author israel
 */
public class ventaDto{
    public String idProducto;
    public String clienteId;
    public List<Item> items;

    public static class Item {
         public String productoId;
         public int cantidad;
    }
}
