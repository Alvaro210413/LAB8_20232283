package edu.pucp.gtics.lab8.entity;

// PASO 4 (ver application.properties): entidad de la tabla "usuarios".
// Implementa Serializable porque el objeto User se guarda en la SESION (UserController).

import jakarta.persistence.*;

import java.io.Serializable;

// Se guarda en sesión (HttpSession), por eso implementa Serializable
@Entity
@Table(name = "usuarios")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idusuario;

    private String apellidos;
    private String nombres;
    private String correo;
    private String password;
    private String autorizacion;   // "ADMIN" o "USER"
    private Boolean enabled;

    public int getIdusuario() { return idusuario; }
    public void setIdusuario(int idusuario) { this.idusuario = idusuario; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAutorizacion() { return autorizacion; }
    public void setAutorizacion(String autorizacion) { this.autorizacion = autorizacion; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
}
