package edu.pucp.gtics.lab8.repository;

import edu.pucp.gtics.lab8.entity.Paises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisesRepository extends JpaRepository<Paises, Integer> {
}
