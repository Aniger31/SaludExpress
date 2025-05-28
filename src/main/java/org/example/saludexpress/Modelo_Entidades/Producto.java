package org.example.saludexpress.Modelo_Entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_Producto")
    private Integer idProducto;

    @Column(name="nombre_producto",length = 255,nullable = false)
    private String nombreProducto;

    @Column(name="descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name="precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name="cantidad_disponible")
    private Integer cantidadDisponible;

    @ManyToOne
    @JoinColumn(name = "idDepartamento")
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedores proveedores;

   //@ManyToMany
    //@JoinTable(
   //         name = "Producto_Proveedor",
   //         joinColumns = @JoinColumn(name = "Id_Producto"),
  //          inverseJoinColumns = @JoinColumn(name = "Id_Proveedor")
  //  )
  //  private List<Proveedores> proveedores;


    @ManyToOne
    @JoinColumn(name = "idMarca")
    private Marca marca;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precionUnitario) {
        this.precioUnitario = precionUnitario;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Proveedores getProveedor() {
       return proveedores;
    }

    public void setProveedor(Proveedores proveedores) {
        this.proveedores = proveedores;
    }



    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

}
