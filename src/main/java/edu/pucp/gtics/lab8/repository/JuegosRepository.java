package edu.pucp.gtics.lab8.repository;

// PASO 5: dos @Query nativas: juegos comprados (devuelve el DTO) y juegos no comprados.
// Las usa JuegosController (paso 10).

import edu.pucp.gtics.lab8.entity.Juegos;
import edu.pucp.gtics.lab8.entity.JuegosUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegosRepository extends JpaRepository<Juegos, Integer> {

    // 2.1 Juegos comprados por un usuario -> lista de JuegosUserDto.
    // Los alias (idjuego, imageURL, nombre, genero, descripcion) deben coincidir
    // con los getters de la interface JuegosUserDto.
    @Query(value = "select j.idjuego as idjuego, j.image as imageURL, j.nombre as nombre, " +
            "g.nombre as genero, j.descripcion as descripcion " +
            "from juegos j " +
            "inner join juegosxusuario jxu on j.idjuego = jxu.idjuego " +
            "inner join generos g on j.idgenero = g.idgenero " +
            "where jxu.idusuario = ?1",
            nativeQuery = true)
    List<JuegosUserDto> obtenerJuegosPorUser(int idusuario);

    // 2.2.1 Juegos que el usuario AÚN NO compró, ordenados por nombre descendente
    @Query(value = "select * from juegos " +
            "where idjuego not in (select idjuego from juegosxusuario where idusuario = ?1) " +
            "order by nombre desc",
            nativeQuery = true)
    List<Juegos> obtenerJuegosNoComprados(int idusuario);
}
