package edu.pucp.gtics.lab8.entity;

// PASO 4: tabla de compras. CarritoController (paso 11) inserta aqui al comprar.

import jakarta.persistence.*;

// Tabla intermedia: qué juegos compró cada usuario
@Entity
@Table(name = "juegosxusuario")
public class JuegosxUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idjuegosxusuario;

    @ManyToOne
    @JoinColumn(name = "idjuego")
    private Juegos juego;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private User usuario;

    private int cantidad;

    public int getIdjuegosxusuario() { return idjuegosxusuario; }
    public void setIdjuegosxusuario(int idjuegosxusuario) { this.idjuegosxusuario = idjuegosxusuario; }
    public Juegos getJuego() { return juego; }
    public void setJuego(Juegos juego) { this.juego = juego; }
    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
