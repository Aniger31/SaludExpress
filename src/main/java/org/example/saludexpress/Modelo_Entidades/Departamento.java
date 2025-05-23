package org.example.saludexpress.Modelo_Entidades;

import jakarta.persistence.*;

@Entity
@Table(name="Departamento")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Departamento")
    private Integer idDepartamento;

    @Column(name = "nombre_departamento",length = 50)
    private String nombreDepartamento;

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }
}

