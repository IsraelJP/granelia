package com.granelia.service;

import beans.registerBean;
import com.granelia.dao.userDao;
import com.granelia.dto.userDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.HashMap;


@ApplicationScoped
public class userService {
    @Inject userDao dao;
    
    public userDto crearUsuario( userDto in) throws SQLException { return  dao.insert(in); }
    public int buscarUsuario(String username, String password) throws SQLException {return dao.buscar(username, password);}

}
