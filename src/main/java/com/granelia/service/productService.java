/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.service;

import com.granelia.dao.productDao;
import com.granelia.dto.productDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author israel
 */
@ApplicationScoped
public class productService {
    @Inject productDao dao;
   
    public List<productDto> catalogo(){
        return dao.catalogo();
    }
    
   
    public productDto crearProducto(productDto producto) throws SQLException {
        System.out.println(producto.getNombre() );
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (producto.getMarca() == null || producto.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("La marca es obligatoria");
        }
        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (producto.getPeso_gramos() <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0");
        }
        
        
        return dao.insert_product(producto);
    }
    
    public List<productDto> buscarProductos(String termino) throws SQLException {
        if (termino == null || termino.trim().isEmpty()) {
            return catalogo();
        }
        return (List<productDto>) dao.buscar_product(termino);
    }
    
    public boolean actualizarProducto(productDto producto) throws SQLException {
        if (producto.getId_producto() == 0) {
            throw new IllegalArgumentException("ID no puede ser 0");
        }
        
        // Recalcular IVA
        producto.setPrecio_iva(producto.getPrecio() * 1.16);
        
        return dao.actualizar_product(producto);
    }
    
    public boolean eliminarProcusto(int id)throws SQLException{
        return dao.eliminar(id);
    }
    
    
    
    
}
