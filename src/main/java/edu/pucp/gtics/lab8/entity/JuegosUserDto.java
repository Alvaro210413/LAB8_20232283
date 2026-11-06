package edu.pucp.gtics.lab8.entity;

// PASO 4: DTO tipo interface. Lo llena la @Query de JuegosRepository (paso 5)
// y se muestra en templates/juegos/comprado.html (paso 12).

// DTO tipo "interface projection" de Spring Data:
// cada getter corresponde a un ALIAS de la @Query nativa en JuegosRepository.
public interface JuegosUserDto {
    Integer getIdjuego();
    String getImageURL();
    String getNombre();
    String getGenero();
    String getDescripcion();
}
