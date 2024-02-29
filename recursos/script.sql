-- Crear la base de datos-- Crear la base de datos
CREATE DATABASE futbol;
-- Crear el esquema "objetos"
CREATE SCHEMA objetos;

-- Cambiar al esquema "objetos"
SET search_path TO objetos;

-- Crear tabla para los datos personales de los jugadores y entrenadores
CREATE TABLE Persona (
    nombre VARCHAR(40),
    edad INT,
    PRIMARY KEY (nombre)
);

-- Crear tabla para la información de los jugadores
CREATE TABLE Jugadores (
    jugador_id SERIAL PRIMARY KEY,
    dorsal INT,
    posicion VARCHAR(40),
    altura DECIMAL,
    nombre_persona VARCHAR(40) REFERENCES Persona(nombre)
);

-- Crear tabla para la información de los equipos
CREATE TABLE Equipos (
    equipo_id SERIAL PRIMARY KEY,
    nombre VARCHAR(40),
    ciudad VARCHAR(40),
    entrenador_persona VARCHAR(40) REFERENCES Persona(nombre)
);

-- Crear tabla para los partidos
CREATE TABLE Partidos (
    partido_id SERIAL PRIMARY KEY,
    fecha DATE,
    equipo_local_id INT REFERENCES Equipos(equipo_id),
    equipo_visitante_id INT REFERENCES Equipos(equipo_id)
);

INSERT INTO Persona (nombre, edad) VALUES
    ('Juan', 30),
    ('Ruben', 25),
    ('Pedro', 28);
     ('Illias', 18);
      ('Cholo', 48);


INSERT INTO Jugadores (dorsal, posicion, altura, nombre_persona) VALUES
    (10, 'Delantero', 1.75, 'Juan'),
    (5, 'Defensa', 1.80, 'Pedro');

INSERT INTO Equipos (nombre, ciudad, entrenador_persona) VALUES
    ('Cultural', 'Leon', 'Ruben'),
    ('Pontevedra', 'Pontevedra', 'Cholo');
