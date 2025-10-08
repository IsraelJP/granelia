package com.granelia.dao;

import com.granelia.dto.userDto;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class userDao {

    @Resource(lookup = "jdbc/granelia") // JTA-managed en Payara
    private DataSource ds;

    @Transactional // <-- clave: asegura commit al finalizar si no hay excepción
    public userDto insert(userDto in) throws SQLException {
        final String sql =
            "INSERT INTO public.users " +
            "(username, email, phone, name_company, contact_name, password, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?) ";

        try (Connection cn = ds.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, in.getUsername());
            ps.setString(2, in.getEmail());
            ps.setString(3, in.getPhone());
            ps.setString(4, in.getNameCompany());
            ps.setString(5, in.getContactName());
            ps.setString(6, in.getPassword());
            ps.setString(7, in.getStatus()); 

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    
                } else {
                    throw new SQLException("INSERT no retornó ID (RETURNING id).");
                }
            }
        }
        return in;
    }
}
