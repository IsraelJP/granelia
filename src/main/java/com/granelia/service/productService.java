/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.granelia.service;

import com.granelia.dao.productDao;
import com.granelia.dto.productDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
    
    
    
}
