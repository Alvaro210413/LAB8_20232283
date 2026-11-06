package edu.pucp.gtics.lab8.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "generos")
public class Generos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idgenero;

    private String nombre;
    private String descripcion;

    public int getIdgenero() { return idgenero; }
    public void setIdgenero(int idgenero) { this.idgenero = idgenero; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
