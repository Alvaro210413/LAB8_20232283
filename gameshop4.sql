-- PASO 3 (ver application.properties): ejecuta este script en MySQL Workbench.
-- =====================================================================
-- Base de datos de prueba para el Lab 8 (GameShop4)
-- Usar SOLO si no tienes el script original del curso.
-- Usuarios de prueba (contraseña 123456 hasheada con BCrypt):
--   ADMIN -> elarios@pucp.pe
--   USER  -> jbarturen@pucp.pe
-- =====================================================================
DROP DATABASE IF EXISTS gameshop4;
CREATE DATABASE gameshop4 CHARACTER SET utf8mb4;
USE gameshop4;

CREATE TABLE paises (
  idpais INT NOT NULL AUTO_INCREMENT,
  iso CHAR(2),
  nombre VARCHAR(80),
  PRIMARY KEY (idpais)
);

CREATE TABLE generos (
  idgenero INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50),
  descripcion VARCHAR(200),
  PRIMARY KEY (idgenero)
);

CREATE TABLE editoras (
  ideditora INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50),
  descripcion VARCHAR(200),
  PRIMARY KEY (ideditora)
);

CREATE TABLE plataformas (
  idplataforma INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50),
  descripcion VARCHAR(200),
  PRIMARY KEY (idplataforma)
);

CREATE TABLE distribuidoras (
  iddistribuidora INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50),
  descripcion VARCHAR(200),
  fundacion INT,
  web VARCHAR(200),
  idsede INT,
  PRIMARY KEY (iddistribuidora),
  FOREIGN KEY (idsede) REFERENCES paises (idpais)
);

CREATE TABLE juegos (
  idjuego INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50),
  descripcion VARCHAR(448),
  precio DOUBLE,
  image VARCHAR(400),
  idgenero INT,
  idplataforma INT,
  ideditora INT,
  iddistribuidora INT,
  PRIMARY KEY (idjuego),
  FOREIGN KEY (idgenero) REFERENCES generos (idgenero),
  FOREIGN KEY (idplataforma) REFERENCES plataformas (idplataforma),
  FOREIGN KEY (ideditora) REFERENCES editoras (ideditora),
  FOREIGN KEY (iddistribuidora) REFERENCES distribuidoras (iddistribuidora)
);

CREATE TABLE usuarios (
  idusuario INT NOT NULL AUTO_INCREMENT,
  apellidos VARCHAR(45),
  nombres VARCHAR(45),
  correo VARCHAR(45) UNIQUE,
  password VARCHAR(65),
  autorizacion VARCHAR(20),
  enabled BIT(1) DEFAULT 1,
  PRIMARY KEY (idusuario)
);

CREATE TABLE juegosxusuario (
  idjuegosxusuario INT NOT NULL AUTO_INCREMENT,
  idjuego INT,
  idusuario INT,
  cantidad INT,
  PRIMARY KEY (idjuegosxusuario),
  FOREIGN KEY (idjuego) REFERENCES juegos (idjuego),
  FOREIGN KEY (idusuario) REFERENCES usuarios (idusuario)
);

CREATE TABLE factura (
  idfactura INT NOT NULL AUTO_INCREMENT,
  fechaEnvio VARCHAR(50),
  tarjeta VARCHAR(50),
  codigoVerificacion VARCHAR(50),
  direccion VARCHAR(500),
  monto FLOAT,
  idjuegosxusuario INT,
  PRIMARY KEY (idfactura),
  FOREIGN KEY (idjuegosxusuario) REFERENCES juegosxusuario (idjuegosxusuario)
);

-- ------------------------- Datos -------------------------
INSERT INTO paises (iso, nombre) VALUES ('JP','Japón'), ('US','Estados Unidos'), ('FR','Francia');

INSERT INTO generos (nombre, descripcion) VALUES
 ('RPG','Juego de rol'), ('Acción','Juegos de acción'), ('Acción-Aventura','Acción y exploración'), ('Deportes','Simulación deportiva');

INSERT INTO editoras (nombre, descripcion) VALUES ('Nintendo EPD','Desarrolladora interna de Nintendo'), ('Capcom','Desarrolladora japonesa');

INSERT INTO plataformas (nombre, descripcion) VALUES
 ('Nintendo Switch','Consola híbrida de Nintendo'), ('Xbox 360','Consola de Microsoft'),
 ('PlayStation 4','Consola de Sony'), ('PlayStation 5','Consola de Sony (2020)');

INSERT INTO distribuidoras (nombre, descripcion, fundacion, web, idsede) VALUES
 ('Nintendo','Empresa japonesa de videojuegos',1889,'https://www.nintendo.com',1),
 ('Capcom','Empresa japonesa de videojuegos',1979,'https://www.capcom.com',1),
 ('Activision','Empresa estadounidense',1979,'https://www.activision.com',2),
 ('Sony Interactive Entertainment','División de videojuegos de Sony',1993,'https://www.sie.com',1),
 ('Ubisoft','Empresa francesa',1986,'https://www.ubisoft.com',3),
 ('Electronic Arts','Empresa estadounidense',1982,'https://www.ea.com',2);

INSERT INTO juegos (nombre, descripcion, precio, image, idgenero, idplataforma, ideditora, iddistribuidora) VALUES
 ('Xenoblade Chronicles: Definitive Edition','Versión remasterizada llamada Xenoblade Chronicles: Definitive Edition para Nintendo Switch.',250.0,'https://placehold.co/300x400?text=Xenoblade',1,1,1,1),
 ('The Legend of Zelda Breath of the Wild','Videojuego de acción-aventura de 2017 de la serie The Legend of Zelda.',200.0,'https://placehold.co/300x400?text=Zelda+BOTW',3,1,1,1),
 ('Resident Evil 6','Videojuego de acción en tercera persona del género dramatic horror publicado en 2012.',120.0,'https://placehold.co/300x400?text=Resident+Evil+6',2,2,2,2),
 ('Leyendas Pokémon: Arceus','Videojuego de rol ambientado en Sinnoh en una época pasada.',160.0,'https://placehold.co/300x400?text=Pokemon+Arceus',1,1,1,1),
 ('Call of Duty : Black Ops II','Segundo juego de la saga Black Ops, ambientado en una guerra futura.',70.0,'https://placehold.co/300x400?text=COD+BO2',2,2,NULL,3),
 ('Assassin Creed Valhalla','Videojuego de acción-aventura con temática vikinga.',100.0,'https://placehold.co/300x400?text=AC+Valhalla',2,3,NULL,5),
 ('Horizon Forbidden West','Épica historia de ciencia ficción post-apocalíptica.',210.0,'https://placehold.co/300x400?text=Horizon+FW',1,4,NULL,4),
 ('FIFA 21','Entrega número 28 de la serie de videojuegos de FIFA.',90.0,'https://placehold.co/300x400?text=FIFA+21',4,3,NULL,6);

-- password = 123456 (BCrypt)
INSERT INTO usuarios (apellidos, nombres, correo, password, autorizacion, enabled) VALUES
 ('Larios','Enrique','elarios@pucp.pe','$2a$10$ZI2hqU5bbFFhqbuJjMSI.O0mLBkldjTBmgrLg5Q4N6mrjsDRtiKf2','ADMIN',1),
 ('Barturen','Jose','jbarturen@pucp.pe','$2a$10$ZI2hqU5bbFFhqbuJjMSI.O0mLBkldjTBmgrLg5Q4N6mrjsDRtiKf2','USER',1);

-- Jose ya compró FIFA 21, Resident Evil 6 y Pokémon Arceus
INSERT INTO juegosxusuario (idjuego, idusuario, cantidad) VALUES (8,2,1), (3,2,1), (4,2,1);
