package org.example.saludexpress.Modelo_Entidades;

import jakarta.persistence.*;

@Entity
@Table(name="Estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_Estado")
    private Integer idEstado;

    @Column(name="nombre_estado",length = 50, nullable = false)
    private String nombreEstado;

    @Column(name="codigo_estado",length = 8, unique = true)
    private String codigoEstado;

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }
}
