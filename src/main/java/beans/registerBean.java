/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import com.granelia.dto.userDto;
import com.granelia.service.userService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.mail.Service;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.sql.SQLException;

/**
 *
 * @author israel
 */
@Named("register")
@SessionScoped
public class registerBean implements Serializable{
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;
    
    @NotBlank(message = "El email es obligatorio")
    private String email;
    
    @NotBlank(message = "El telefono es obligatorio")
    private String phone;
    
    private String nameCompany;
    private String contactName;
    private String password;
    private String status;

    @Inject
    userService service;

    public String registro() {
        userDto userNew = new userDto();
        userNew.setUsername(username);
        userNew.setEmail(email);
        userNew.setPhone(phone);
        userNew.setNameCompany(nameCompany);
        userNew.setContactName(contactName);
        userNew.setPassword(password);
        userNew.setStatus("activo");
        try {
            service.crearUsuario(userNew);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Insertado", null));
            return null;

        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error: " + e.getMessage(), null));
            return null;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
