/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.dao;


import com.granelia.dto.userDto;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author israel
 */
@jakarta.enterprise.context.ApplicationScoped
public class userDao {
    @Resource(lookup = "jdbc/granelia")
    private DataSource ds;
        
        public userDto insert(userDto in) throws SQLException {
        String sql = " INSERT INTO users (username, email, phone, name_company, contact_name) VALUES (?,?,?,?,?) RETURNING id";
        try (Connection cn = ds.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, in.getUsername());
            ps.setString(2, in.getEmail());
            ps.setString(3, in.getPhone());
            ps.setString(4, in.getNameCompany());
            ps.setString(5, in.getContactName());
            try (ResultSet rs = (ResultSet) ps.executeQuery()) {
                if (rs.next());
            }
        }
        return in;
    }
    
}
