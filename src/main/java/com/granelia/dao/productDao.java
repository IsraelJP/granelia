/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.dao;

import com.granelia.dto.productDto;
import com.granelia.service.productService;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author israel
 */
public class productDao {

    @Resource(lookup = "jdbc/granelia")
    private DataSource ds;

    public List<productDto> catalogo() {
        
      List<productDto> list = new ArrayList<>();

        String sql = "SELECT * FROM PRODUCTOS_GRANELIA";

        try (Connection cn = ds.getConnection(); Statement ps = cn.createStatement(); ResultSet rs = ps.executeQuery(sql); ) {
            
            while(rs.next()){
             productDto dto = new productDto();
             dto.setNombre(rs.getString("nombre"));
             dto.setMarca(rs.getString("marca"));
             dto.setCategoria(rs.getString("categoria"));
             dto.setPeso_gramos(rs.getInt("peso_gramos"));
             dto.setPrecio(rs.getDouble("precio"));
             dto.setPrecio_iva(rs.getDouble("precio_con_iva"));
             dto.setStock(rs.getInt("stock"));
             list.add(dto);
             
            }
            return list;
        } catch (SQLException e) {
            return null;
        }

    }

}
