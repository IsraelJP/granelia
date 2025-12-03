package com.granelia.dao;

import com.granelia.dto.productDto;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class productDao {
    @Resource(lookup = "jdbc/granelia")
    private DataSource ds;

    public List<productDto> catalogo() {
        List<productDto> list = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTOS_GRANELIA";
        try (Connection cn = ds.getConnection(); 
             Statement ps = cn.createStatement(); 
             ResultSet rs = ps.executeQuery(sql)) {
            
            while(rs.next()){
                productDto dto = new productDto();
                dto.setId_producto(rs.getInt("id_producto"));
                dto.setNombre(rs.getString("nombre"));
                dto.setMarca(rs.getString("marca"));
                dto.setCategoria(rs.getString("categoria"));
                dto.setPeso_gramos(rs.getInt("peso_gramos"));
                dto.setPrecio(rs.getDouble("precio"));
                dto.setPrecio_iva(rs.getDouble("precio_iva"));
                dto.setStock(rs.getInt("stock"));
                dto.setImagen(rs.getString("imagen")); // NUEVO
                list.add(dto);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public productDto insert_product(productDto product) throws SQLException {
        final String sql = "INSERT INTO PRODUCTOS_GRANELIA " +
                "(NOMBRE, MARCA, CATEGORIA, PESO_GRAMOS, PRECIO, PRECIO_IVA, STOCK, IMAGEN) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; // AGREGADO IMAGEN
        
        try (Connection cn = ds.getConnection(); 
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, product.getNombre());
            ps.setString(2, product.getMarca());
            ps.setString(3, product.getCategoria());
            ps.setInt(4, product.getPeso_gramos());
            ps.setDouble(5, product.getPrecio());
            ps.setDouble(6, product.getPrecio_iva());
            ps.setInt(7, product.getStock());
            ps.setString(8, product.getImagen()); // NUEVO
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Producto insertado: " + product.getNombre());
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar producto: " + e.getMessage());
            throw e;
        }
        return product;
    }

    public productDto buscar_product(String idProducto) throws SQLException {
        final String sql = "SELECT * FROM PRODUCTOS_GRANELIA WHERE ID_PRODUCTO = ?";
        try (Connection cn = ds.getConnection(); 
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    productDto dto = new productDto();
                    dto.setId_producto(rs.getInt("ID_PRODUCTO"));
                    dto.setNombre(rs.getString("NOMBRE"));
                    dto.setMarca(rs.getString("MARCA"));
                    dto.setCategoria(rs.getString("CATEGORIA"));
                    dto.setPeso_gramos(rs.getInt("PESO_GRAMOS"));
                    dto.setPrecio(rs.getDouble("PRECIO"));
                    dto.setPrecio_iva(rs.getDouble("PRECIO_IVA"));
                    dto.setStock(rs.getInt("STOCK"));
                    dto.setImagen(rs.getString("IMAGEN")); // NUEVO
                    return dto;
                }
            }
        }
        return null;
    }

    @Transactional
    public boolean actualizar_product(productDto product) throws SQLException {
        final String sql = "UPDATE PRODUCTOS_GRANELIA SET " +
                "NOMBRE = ?, MARCA = ?, CATEGORIA = ?, PESO_GRAMOS = ?, " +
                "PRECIO = ?, PRECIO_IVA = ?, STOCK = ?, IMAGEN = ? " + // AGREGADO IMAGEN
                "WHERE ID_PRODUCTO = ?";
        
        try (Connection cn = ds.getConnection(); 
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, product.getNombre());
            ps.setString(2, product.getMarca());
            ps.setString(3, product.getCategoria());
            ps.setInt(4, product.getPeso_gramos());
            ps.setDouble(5, product.getPrecio());
            ps.setDouble(6, product.getPrecio_iva());
            ps.setInt(7, product.getStock());
            ps.setString(8, product.getImagen()); // NUEVO
            ps.setInt(9, product.getId_producto());
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Producto actualizado: " + product.getNombre());
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public boolean eliminar(int idProducto) throws SQLException {
        final String sql = "DELETE FROM PRODUCTOS_GRANELIA WHERE ID_PRODUCTO = ?";
        try (Connection cn = ds.getConnection(); 
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, idProducto);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Producto eliminado ID: " + idProducto);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar: " + e.getMessage());
            throw e;
        }
    }
}