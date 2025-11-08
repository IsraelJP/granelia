/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import com.granelia.dto.productDto;
import com.granelia.service.productService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author israel
 */
@Named("products")
@SessionScoped
public class productBean implements Serializable {

    @Inject
    productService ser;
    private List<productDto> list = new ArrayList<>();
    
    public void cargarLista() {
        try {
            list = ser.catalogo();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuarios cargados", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error cargando usuarios: " + e.getMessage(), null));
        }
    }
    
    public List<productDto>  catalogo(){
    return list;
    }

}
