package edu.pucp.gtics.lab8.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "juegos")
public class Juegos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idjuego;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String nombre;

    @Size(max = 448, message = "Máximo 448 caracteres")
    private String descripcion;

    @NotNull(message = "Ingrese un precio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    // En la BD la columna se llama "image" (URL de la carátula)
    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "idgenero")
    private Generos genero;

    @ManyToOne
    @JoinColumn(name = "idplataforma")
    private Plataformas plataforma;

    // No hay entidad Editoras en la estructura del anexo -> se guarda solo el id
    private Integer ideditora;

    @ManyToOne
    @JoinColumn(name = "iddistribuidora")
    private Distribuidoras distribuidora;

    public int getIdjuego() { return idjuego; }
    public void setIdjuego(int idjuego) { this.idjuego = idjuego; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Generos getGenero() { return genero; }
    public void setGenero(Generos genero) { this.genero = genero; }
    public Plataformas getPlataforma() { return plataforma; }
    public void setPlataforma(Plataformas plataforma) { this.plataforma = plataforma; }
    public Integer getIdeditora() { return ideditora; }
    public void setIdeditora(Integer ideditora) { this.ideditora = ideditora; }
    public Distribuidoras getDistribuidora() { return distribuidora; }
    public void setDistribuidora(Distribuidoras distribuidora) { this.distribuidora = distribuidora; }
}
