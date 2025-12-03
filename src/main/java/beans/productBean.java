package beans;

import com.granelia.dto.productDto;
import com.granelia.service.productService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named("products")
@SessionScoped
public class productBean implements Serializable {
    
    @Inject
    productService ser;
    
    private List<productDto> list = new ArrayList<>();
    private String nombre;
    private String id_producto;
    private String cateogoria;
    private String marca;
    private int preso_gramos;
    private double precio;
    private double precio_iva;
    private int stock;
    private String terminoBusqueda;
    private productDto productoActual = new productDto();
    
    // NUEVO: Para manejar la subida de archivos
    private Part file;
    private String imagenNombre;
    
    // NUEVO: Getters y Setters para file
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getImagenNombre() {
        return imagenNombre;
    }

    public void setImagenNombre(String imagenNombre) {
        this.imagenNombre = imagenNombre;
    }
    
    public productDto getProductoActual() {
        return productoActual;
    }

    public void setProductoActual(productDto productoActual) {
        this.productoActual = productoActual;
    }

    public void cargarLista() {
        try {
            list = ser.catalogo();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Productos Cargados Correctamente", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Productos: " + e.getMessage(), null));
        }
    }

    public String marcarEliminar() {
        if (productoActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "No se seleccionó ningún producto para eliminar", null));
        } else {
            eliminarProducto();
            return "catalogo?faces-redirect=true";
        }
        return "";
    }

    public List<productDto> catalogo() {
        return list;
    }


    public void crearProducto() {
        try {
            productDto nuevo = new productDto();
            nuevo.setNombre(nombre);
            nuevo.setCategoria(cateogoria);
            nuevo.setMarca(marca);
            nuevo.setPeso_gramos(preso_gramos);
            nuevo.setPrecio(precio);
            double precioConIva = precio * 1.16;
            nuevo.setPrecio_iva(precioConIva);
            nuevo.setStock(stock);
            
            if (file != null) {
                try (InputStream is = file.getInputStream()) {
                    nuevo.setImagenBytes(is.readAllBytes());
                    System.out.println(is.readAllBytes());
                    nuevo.setImagenContentType(file.getContentType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
   
            ser.crearProducto(nuevo);
            cargarLista();
            limpiarFormulario();
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto creado exitosamente", null));
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + e.getMessage(), null));
        }
    }

    private void limpiarFormulario() {
        nombre = null;
        cateogoria = null;
        marca = null;
        preso_gramos = 0;
        precio = 0.0;
        precio_iva = 0.0;
        stock = 0;
        file = null;
        imagenNombre = null;
    }


    public void eliminarProducto() {
        try {
            boolean eliminado = ser.eliminarProcusto(productoActual.getId_producto());
            cargarLista();
            limpiarFormulario();
            
            if (eliminado) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto eliminado", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "No se encontró el producto", null));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + e.getMessage(), null));
        }
    }


    /**
        * Prepara el producto para editar y navega a la página de edición
        */
       public String prepararEditar(productDto producto) {
           this.productoActual = producto;
           return "editarProducto?faces-redirect=true";
       }

       /**
        * Prepara el producto para eliminar y navega a la página de confirmación
        */
       public String prepararEliminar(productDto producto) {
           this.productoActual = producto;
           return "eliminarProducto?faces-redirect=true";
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

    public String getTerminoBusqueda() {
        return terminoBusqueda;
    }

    public void setTerminoBusqueda(String terminoBusqueda) {
        this.terminoBusqueda = terminoBusqueda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCateogoria() {
        return cateogoria;
    }

    public void setCateogoria(String cateogoria) {
        this.cateogoria = cateogoria;
    }

    public int getPreso_gramos() {
        return preso_gramos;
    }

    public void setPreso_gramos(int preso_gramos) {
        this.preso_gramos = preso_gramos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecio_iva() {
        return precio_iva;
    }

    public void setPrecio_iva(double precio_iva) {
        this.precio_iva = precio_iva;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}