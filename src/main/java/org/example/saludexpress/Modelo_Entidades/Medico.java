package org.example.saludexpress.Modelo_Entidades;

import jakarta.persistence.*;

@Entity
@Table(name="Medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_Medico")
    private Integer idMedico;

    @Column(name = "especialidad",length = 50)
    private String especialidad;

    @Column(name = "cedula_profesional", length = 20)
    private String cedulaProfesional;

    @OneToOne
    @MapsId
    @JoinColumn(name = "Id_Medico", referencedColumnName = "Id_Empleado", insertable = false, updatable = false)
    private Empleado empleado;

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }


    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}
