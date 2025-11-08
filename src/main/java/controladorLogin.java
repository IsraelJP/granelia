
import com.granelia.service.userService;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.sql.SQLException;

@Named("controladorLogin")
@SessionScoped
public class controladorLogin implements Serializable {

    private String uid;
    private String password;
    private String resultado;

    @Inject
    userService service;

    public String login() throws SQLException {
        try {
            int x = service.buscarUsuario(uid, password);
            if (x == 1) {
                return "panel?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Usuario o contraseña incorrectos", null));
                return null;
            }
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                 "Error: " + e.getMessage(), null));
            return null;
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

}
