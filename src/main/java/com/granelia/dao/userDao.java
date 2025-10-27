package com.granelia.dao;

import com.granelia.dto.userDto;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;

public class userDao {

    @Resource(lookup = "jdbc/granelia") // DataSource JTA en Payara
    private DataSource ds;

    @Transactional
    public userDto insert(userDto in) throws SQLException {
        final String sql = 
            "INSERT INTO public.users " +
            "(username, email, phone, name_company, contact_name, password, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = ds.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, in.getUsername());
            ps.setString(2, in.getEmail());
            ps.setString(3, in.getPhone());
            ps.setString(4, in.getNameCompany());
            ps.setString(5, in.getContactName());
            ps.setString(6, in.getPassword());
            ps.setString(7, in.getStatus());

            int rows = ps.executeUpdate(); // 👈 usa executeUpdate en lugar de executeQuery

            if (rows > 0) {
                System.out.println("✅ Registro insertado correctamente para el usuario: " + in.getUsername());
            } else {
                System.out.println("⚠️ No se insertó ningún registro en la tabla users.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar usuario: " + e.getMessage());
            throw e;
        }

        return in;
    }
    
    public boolean buscar(String username , String password) throws SQLException {
        String username1 = username;
        String password1 = password;
        if (username1 == null || password1 == null) return false;

        final String sql =
            "SELECT 1 FROM public.users WHERE username = ? AND password = ? LIMIT 1";

        try (Connection cn = ds.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true si encontró coincidencia
            }
        }
    }

}
