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
import java.sql.SQLException;
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
    private productDto productoActual = new productDto();
    private String terminoBusqueda;
    
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
    
    
    
    
     public String crearProducto() {
        try {
            ser.crearProducto(productoActual);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Producto creado exitosamente", null));
            productoActual = new productDto();
            cargarLista();
            return "catalogo?faces-redirect=true";
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, 
                    e.getMessage(), null));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error: " + e.getMessage(), null));
            return null;
        }
    }
     
    public String actualizarProducto() {
        try {
            boolean actualizado = ser.actualizarProducto(productoActual);
            if (actualizado) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Producto actualizado", null));
                productoActual = new productDto();
                return "catalogo?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, 
                        "No se pudo actualizar", null));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error: " + e.getMessage(), null));
            return null;
        }
    }
    
    public void eliminarProducto(int idProducto) {
        try {
            boolean eliminado = ser.eliminarProcusto(idProducto);
            if (eliminado) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Producto eliminado", null));
                cargarLista();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, 
                        "No se encontró el producto", null));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error: " + e.getMessage(), null));
        }
    }
    
    public void buscarProductos() {
        try {
            if (terminoBusqueda == null || terminoBusqueda.trim().isEmpty()) {
                cargarLista();
            } else {
                list = ser.buscarProductos(terminoBusqueda);
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Encontrados: " + list.size() + " productos", null));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error: " + e.getMessage(), null));
        }
    }

    public productService getSer() {
        return ser;
    }

    public void setSer(productService ser) {
        this.ser = ser;
    }

    public List<productDto> getList() {
        return list;
    }

    public void setList(List<productDto> list) {
        this.list = list;
    }

    public productDto getProductoActual() {
        return productoActual;
    }

    public void setProductoActual(productDto productoActual) {
        this.productoActual = productoActual;
    }

    public String getTerminoBusqueda() {
        return terminoBusqueda;
    }

    public void setTerminoBusqueda(String terminoBusqueda) {
        this.terminoBusqueda = terminoBusqueda;
    }
    
    
     

    
    
}
