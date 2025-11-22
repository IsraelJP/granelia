/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import com.granelia.dto.pagoDto;
import com.granelia.service.pagoService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named("pagos")
@SessionScoped
public class pagoBean implements Serializable {

    @Inject
    pagoService ser;

    private List<pagoDto> list = new ArrayList<>();
    
    // Campos del formulario
    private String numeroTransaccion;
    private double monto;
    private LocalDate fechaPago;
    private String titular;
    private String filtroEstado = "TODOS";
    private String terminoBusqueda;
    
    // Pago seleccionado
    private pagoDto pagoActual = new pagoDto();

    // Cargar lista de pagos
    public void cargarLista() {
        try {
            list = ser.listarPagos();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "✓ " + list.size() + " pagos cargados", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error al cargar pagos: " + e.getMessage(), null));
        }
    }

    // Registrar nuevo pago (estado viene del banco)
    public void registrarPago() {
        try {
            pagoDto nuevo = new pagoDto();
            nuevo.setNumero_transaccion(numeroTransaccion);
            nuevo.setMonto(monto);
            nuevo.setFecha_pago(fechaPago);
            nuevo.setTitular(titular);

            pagoDto registrado = ser.registrarPago(nuevo);
            cargarLista();
            limpiarFormulario();

            // Mostrar mensaje según el estado del banco
            String msgEstado = "";
            switch (registrado.getEstado()) {
                case "VALIDADO":
                    msgEstado = "✓ Transacción VALIDADA por el banco";
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msgEstado, null));
                    break;
                case "PENDIENTE":
                    msgEstado = "⏳ Transacción PENDIENTE de validación";
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msgEstado, null));
                    break;
                case "RECHAZADO":
                    msgEstado = "✗ Transacción RECHAZADA por el banco";
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msgEstado, null));
                    break;
            }

        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + e.getMessage(), null));
        }
    }

    // Buscar por número de transacción
    public void buscarPago() {
        try {
            if (terminoBusqueda == null || terminoBusqueda.trim().isEmpty()) {
                cargarLista();
            } else {
                pagoDto encontrado = ser.buscarPorTransaccion(terminoBusqueda);
                list.clear();
                if (encontrado != null) {
                    list.add(encontrado);
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "✓ Transacción encontrada", null));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No se encontró la transacción", null));
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + e.getMessage(), null));
        }
    }

    // Filtrar por estado
    public void filtrarPorEstado() {
        try {
            list = ser.filtrarPorEstado(filtroEstado);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "✓ Mostrando: " + list.size() + " pagos", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + e.getMessage(), null));
        }
    }

    // Eliminar pago
    public String eliminarPago() {
        try {
            if (pagoActual == null || pagoActual.getId_pago() == 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Seleccione un pago primero", null));
                return null;
            }

            boolean eliminado = ser.eliminarPago(pagoActual.getId_pago());
            if (eliminado) {
                cargarLista();
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "✓ Pago eliminado", null));
            }
            pagoActual = new pagoDto();
            return null;

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + e.getMessage(), null));
            return null;
        }
    }

    private void limpiarFormulario() {
        numeroTransaccion = null;
        monto = 0.0;
        fechaPago = null;
        titular = null;
    }

    // Método para obtener clase CSS según estado
    public String getEstadoClass(String estado) {
        if (estado == null) return "bg-secondary";
        switch (estado) {
            case "VALIDADO": return "bg-success";
            case "RECHAZADO": return "bg-danger";
            case "PENDIENTE": return "bg-warning text-dark";
            default: return "bg-secondary";
        }
    }

    // Getters y Setters
    public List<pagoDto> getList() { return list; }
    public void setList(List<pagoDto> list) { this.list = list; }

    public String getNumeroTransaccion() { return numeroTransaccion; }
    public void setNumeroTransaccion(String numeroTransaccion) { this.numeroTransaccion = numeroTransaccion; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    public String getFiltroEstado() { return filtroEstado; }
    public void setFiltroEstado(String filtroEstado) { this.filtroEstado = filtroEstado; }

    public String getTerminoBusqueda() { return terminoBusqueda; }
    public void setTerminoBusqueda(String terminoBusqueda) { this.terminoBusqueda = terminoBusqueda; }

    public pagoDto getPagoActual() { return pagoActual; }
    public void setPagoActual(pagoDto pagoActual) { this.pagoActual = pagoActual; }
}