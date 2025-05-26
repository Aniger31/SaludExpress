package org.example.saludexpress.Modelo_Entidades;

import jakarta.persistence.*;


@Entity
@Table(name="Sucursal")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Sucursal")
    private Integer idSucursal;

    @Column(name = "telefono",length = 20)
    private String telefono;

    @Column(name = "calle",length = 50)
    private String calle;

    @Column(name = "numero")
    private Integer numero;

    @Column(name="colonia",length = 50)
    private String colonia;

    @Column(name="codigo_postal",length = 7)
    private String codigoPostal;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "Nombre", referencedColumnName = "nombre")
    private Farmacia farmacia;

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }
}

