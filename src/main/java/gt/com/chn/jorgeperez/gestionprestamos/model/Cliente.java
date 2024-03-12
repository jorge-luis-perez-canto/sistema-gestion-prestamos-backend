package gt.com.chn.jorgeperez.gestionprestamos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * La clase Cliente representa un cliente en el sistema de gestión de préstamos bancarios.
 * Contiene información personal del cliente, así como una relación con los préstamos que el cliente haya solicitado.
 */

@Entity
@Table(name = "cliente")

public class Cliente {

    /**
     * Identificador único del cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long clienteId;

    /**
     * Nombre del cliente.
     */
    @NotEmpty
    @Length(max = 50)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Apellido del cliente.
     */
    @NotEmpty
    @Length(max = 50)
    @Column(name = "apellido")
    private String apellido;

    /**
     * Número único de identificación del cliente.
     */
    @NotNull
    @Column(length = 20, name = "numero_identificacion")
    private String numeroIdentificacion;

    /**
     * Fecha de nacimiento del cliente.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    /**
     * Dirección residencial del cliente.
     */
    @NotEmpty
    @Length(max = 100)
    @Column(name = "direccion")
    private String direccion;

    /**
     * Correo electrónico del cliente.
     */
    @Email
    @NotEmpty
    @Length(max = 50)
    @Column(name = "correo_electronico")
    private String correoElectronico;

    /**
     * Número de teléfono del cliente.
     */
    @NotEmpty
    @Length(max = 20)
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "activo")
    private boolean activo = true; // Todos los clientes están activos por defecto.


    /**
     * Conjunto de préstamos solicitados por el cliente.
     */
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Prestamo> prestamos = new HashSet<>();

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Set<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return "Cliente{" + "clienteId=" + clienteId + ", nombre='" + nombre + '\'' + ", apellido='" + apellido + '\'' + ", numeroIdentificacion='" + numeroIdentificacion + '\'' + ", fechaNacimiento=" + fechaNacimiento + ", direccion='" + direccion + '\'' + ", correoElectronico='" + correoElectronico + '\'' + ", telefono='" + telefono + '\'' + ", activo=" + activo + ", prestamos=" + prestamos + '}';
    }
}
