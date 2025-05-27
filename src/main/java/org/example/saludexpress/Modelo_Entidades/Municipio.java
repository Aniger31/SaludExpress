package org.example.saludexpress.Modelo_Entidades;

import jakarta.persistence.*;

@Entity
@Table(
        name = "municipio",
        uniqueConstraints = @UniqueConstraint(columnNames = {"nombreMunicipio", "idEstado"})
)
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_Municipio")
    private Integer idMunicipio;

    @Column(name="nombre_municipio",length = 50, nullable = false)
    private String nombreMunicipio;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
