package com.granelia.service;

import com.granelia.dao.userDao;
import com.granelia.dto.userDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;


@ApplicationScoped
public class userService {
    @Inject userDao dao;


    public userDto crearUsuario(userDto in) throws SQLException { return dao.insert(in); }
}
