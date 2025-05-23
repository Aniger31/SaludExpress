package org.example.saludexpress.Modelo_Entidades;

import jakarta.persistence.*;


@Entity
@Table(name="Farmacia")
public class Farmacia {

    @Id
    @Column(name = "Nombre",length = 100)
    private String nombre;

    @Column(name = "razon_social",length = 100)
    private String razonSocial;

    @Column(name = "sede_central",length = 100)
    private String sedeCentral;

    @Column(name="telefono",length = 20)
    private String telefono;

    @Column(name="correo",length = 100)
    private String correo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getSedeCentral() {
        return sedeCentral;
    }

    public void setSedeCentral(String sedeCentral) {
        this.sedeCentral = sedeCentral;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
