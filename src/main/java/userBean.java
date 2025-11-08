
import com.granelia.dto.User;
import com.granelia.service.ApiService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author israel
 */
@Named("usersBean")
@ViewScoped
public class userBean implements Serializable {

    @Inject
    private ApiService api;
    private List<User> usuarios = new ArrayList<>();
    private User seleccionado;

    public void cargar() {
        try {
            usuarios = api.listUsers();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuarios cargados", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error cargando usuarios: " + e.getMessage(), null));
        }
    }
    
     public List<User> getUsuarios() { return usuarios; }
    public User getSeleccionado() { return seleccionado; }

}
