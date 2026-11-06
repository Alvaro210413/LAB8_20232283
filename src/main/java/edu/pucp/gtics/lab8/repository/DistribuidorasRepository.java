package edu.pucp.gtics.lab8.repository;

import edu.pucp.gtics.lab8.entity.Distribuidoras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistribuidorasRepository extends JpaRepository<Distribuidoras, Integer> {
}
