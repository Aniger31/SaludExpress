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
}
