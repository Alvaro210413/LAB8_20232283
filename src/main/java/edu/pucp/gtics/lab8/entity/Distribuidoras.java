package edu.pucp.gtics.lab8.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "distribuidoras")
public class Distribuidoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iddistribuidora;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String nombre;

    @Size(max = 200, message = "Máximo 200 caracteres")
    private String descripcion;

    private Integer fundacion;

    private String web;

    @ManyToOne
    @JoinColumn(name = "idsede")
    private Paises sede;

    public int getIddistribuidora() { return iddistribuidora; }
    public void setIddistribuidora(int iddistribuidora) { this.iddistribuidora = iddistribuidora; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getFundacion() { return fundacion; }
    public void setFundacion(Integer fundacion) { this.fundacion = fundacion; }
    public String getWeb() { return web; }
    public void setWeb(String web) { this.web = web; }
    public Paises getSede() { return sede; }
    public void setSede(Paises sede) { this.sede = sede; }
}
