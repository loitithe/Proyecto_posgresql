CREATE SCHEMA objetos;

--  tipo para la informacion de la persona
-- Cambiar al esquema "objetos"
SET search_path TO objetos;
CREATE TYPE Persona as(
    nombre text,
    edad int
);

-- tipo para la informacion del jugador
CREATE TYPE Jugador as(
    dorsal int,
    posicion text,
	altura float
);
--  tipo para la informacion del equipo
CREATE TYPE Equipo as(
    nombre text,
    ciudad text,
    entrenador_persona Persona 
);

--  tabla para la informacion de los equipos
CREATE TABLE Equipos (
	equipo_id SERIAL PRIMARY KEY,
	equipo_info Equipo 
);

-- tabla para la informacion de los jugadores
CREATE TABLE Jugadores (
    jugador_id SERIAL PRIMARY KEY,
   datos_personales Persona,
	jugador_info Jugador,
    equipo_id integer not null,
	FOREIGN KEY (equipo_id) references objetos.Equipos(equipo_id)
	
);
--  tabla para los partidos
CREATE TABLE Partidos (
    partido_id SERIAL PRIMARY KEY,
    fecha DATE,
	equipo_local_id int not null,
	equipo_visitante_id int not null,
    FOREIGN KEY(equipo_local_id) REFERENCES obejtos.Equipos(equipo_id),
   FOREIGN KEY(equipo_visitante_id)REFERENCES obejtos.Equipos(equipo_id) 
);


INSERT INTO objetos.Jugadores (datos_personales,jugador_info,equipo_id) VALUES(
    ROW('Juan',30),ROW(10, 'Delantero', 1.75),1
    );

INSERT INTO objetos.Equipos (equipo_info) VALUES(
    Row('Vigo', 'celta', ROW('Cholo',22))
	
);
INSERT INTO objetos.Equipos (equipo_info) VALUES(
    Row('Madrid', 'Real', ROW('Ance',66))
	
);
INSERT INTO objetos.Equipos (equipo_info) VALUES(
    Row('Depor', 'Coruña', ROW('Cholo',22))
	
);
INSERT INTO objetos.Equipos (equipo_info) VALUES(
    Row('Vigo', 'celta', ROW('Cholo',22))
	
);

INSERT INTO objetos.Partidos (date,equipo_local_id,equipo_visitante_id) VALUES(
    '13-03-2022',1,2
	
);