package edu.pucp.gtics.lab8.repository;

// PASO 5: query method findByCorreo, usado en UserController.signInRedirect (paso 8).

import edu.pucp.gtics.lab8.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Query method: Spring genera "select * from usuarios where correo = ?"
    // Se usa en signInRedirect para traer al usuario logueado (su username es el correo)
    User findByCorreo(String correo);
}
