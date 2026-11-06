package edu.pucp.gtics.lab8.repository;

import edu.pucp.gtics.lab8.entity.JuegosxUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuegosxUsuarioRepository extends JpaRepository<JuegosxUsuario, Integer> {
}
