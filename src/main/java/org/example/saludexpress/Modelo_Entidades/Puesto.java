package org.example.saludexpress.Modelo_Entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="Puesto")
public class Puesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Puesto")
    private Integer idPuesto;

    @Column(name = "nombre_puesto", length = 50, nullable = false)
    private String nombrePuesto;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "sueldo_base", precision = 10, scale = 2, nullable = false)
    private BigDecimal sueldoBase;

    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(BigDecimal sueldoBase) {
        this.sueldoBase = sueldoBase;
    }
}
