import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    /**
     * Tareas a realizar
     * Se pide:
     *
     * Crear el script .sql que permita crear la base de datos PostgreSQL futbol.
     *
     * El script debe permitir crear la estructura de la base de datos.
     * Debe insertar datos suficientes que permita verificar el correcto
     * funcionamiento de las siguientes consultas.
     * 
     * 
     */
    private static Connection connection;
    static Scanner sc;
    private static PreparedStatement pStatement = null;
    private static Statement statement = null;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void menuPrincipal() {
        int option = -1;
        do {
            System.out.println("1.Menu modificacion de datos");
            System.out.println("2.Menu consulta de datos\n0.Salir");
            sc = new Scanner(System.in);
            option = pedirInt("Elige una opcion ");
            switch (option) {
                case 1:
                    menu1();
                    break;
                case 2:
                    menu2();
                default:
                    break;
            }
        } while (option != 0);

    }

    public static void menu1() {
        String url = "jdbc:postgresql://localhost/futbol";
        String user = "postgres";
        String password = "abc123";
        int option = -1;
        try {
            connection = DriverManager.getConnection(url, user, password);
            sc = new Scanner(System.in);
            do {
                System.out.println("1 # Insertar equipo #\n2-Insertar jugador\n3 $ Insertar partido $ ");
                System.out.println("4 # Eliminar equipo #\n5-Eliminar jugador\n6 $ Eliminar partido $ ");
                System.out.println("7 # Modificar equipo #\n8-Modificar jugador\n9 $ Modificar partido $\n0.Salir");
                option = sc.nextInt();

                switch (option) {
                    case 1:
                        insertarEquipo(pedirString("nombre equipo"), pedirString("ciudad equipo"),
                                pedirString("nombre entrenador"), pedirInt("edad entrenador"));
                        break;
                    case 2:
                        insertarJugador(pedirString("Nombre jugador"), pedirInt("Edad jugador"), pedirInt("Dorsal"),
                                pedirString("Posicion"), pedirFloat("Altura"), pedirInt("Id del equipo del jugador"));
                        break;
                    case 3:
                        insertarPartido(pedirFecha("Introduce una fecha"), pedirInt("Introduce id equipo local"),
                                pedirInt("Introduce id equipo visitante"));
                        break;
                    case 4:
                        eliminarEquipo(pedirInt("Id del equipo a eliminar"));
                        break;
                    case 5:
                        eliminarJugador(pedirInt("Id del jugador a eliminar"));
                        break;
                    case 6:
                        eliminarPartido(pedirInt("Id del partido a eliminar"));
                        break;
                    case 7:
                        modificarEquipo(pedirInt(" Id del equipo a modificar:"),
                                pedirString("Nuevo nombre equipo"),
                                pedirString("Nueva ciudad equipo"), pedirString("Nuevo nombre entrenador"),
                                pedirInt("Nueva edad entrenador"));
                        break;
                    case 8:
                        modificarJugador(pedirInt("Id del jugador a modificar:"),
                                pedirString("Nuevo nombre jugador"),
                                pedirInt("Nueva edad jugador"), pedirInt("Nuevo dorsal"),
                                pedirString("Nueva posición"), pedirFloat("Nueva altura"),
                                pedirInt("Nuevo ID del equipo del jugador"));
                        break;
                    case 9:
                        modificarPartido(pedirInt(" Id del partido a modificar:"), pedirFecha("Nueva fecha"),
                                pedirInt("Nuevo Id equipo local"), pedirInt("Nuevo Id equipo visitante"));

                        break;
                    default:
                        break;
                }
            } while (option != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void menu2() {
        String url = "jdbc:postgresql://localhost/futbol";
        String user = "postgres";
        String password = "abc123";
        int option = -1;
        try {
            connection = DriverManager.getConnection(url, user, password);
            sc = new Scanner(System.in);
            do {
                System.out.println("1. Listar toda la información de un Equipo buscándolo por id.\r\n" + //
                        "2. Listar toda la información de todos los Equipos.\r\n" + //
                        "3. Listar la información de un Jugador buscándolo por id.\r\n" + //
                        "4. Listar la información de un Jugador buscándolo por nombre.\r\n" + //
                        "5. Buscar partidos en los que un determinado equipo jugara como local.\r\n" + //
                        "6. Buscar partidos en los que un determinado equipo jugara como visitante.\r\n" + //
                        "7. Obtener toda la información de los jugadores que jueguen en una determinada posición.\n"
                        +
                        "8. Obtener toda la información de los jugadores según su dorsal.\r\n" + //
                        "9. Obtener todos los partidos según la fecha.\n0. Salir");
                option = sc.nextInt();

                switch (option) {
                    case 1:
                        listarEquipoPorId(pedirInt("Introduce id del equipo "));
                        break;
                    case 2:
                        listarTodosEquipos();
                        break;
                    case 3:
                        listarJugadorPorId(pedirInt("Introduce id del jugador"));
                        break;
                    case 4:
                        listarJugadorPorNombre(pedirString("Introduce el nombre del jugador"));
                        break;
                    case 5:
                        buscarPartidosLocal(pedirInt("Introduce id del equipo a buscar"));
                        break;
                    case 6:
                        buscarPartidosVisitante(pedirInt("Introduce el id del equipo a buscar"));
                        break;
                    case 7:
                        obtenerJugadoresPorPosicion(pedirString("Introduce una posicion"));
                        break;
                    case 8:
                        obtenerJugadoresPorDorsal(pedirInt("Introduce el dorsal de los jugadores a buscar"));
                        break;
                    case 9:
                        obtenerPartidosPorFecha(pedirFecha("Introduce fecha para buscar el partido"));
                        break;
                    default:
                        break;
                }
            } while (option != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        // menu1();
        // menu2();
        menuPrincipal();
    }

    /**
     * * Crear los métodos que permitan:
     * insertar, eliminar y modificar un Equipo.
     * insertar, eliminar y modificar un Jugador.
     * insertar, eliminar y modificar un Partido.
     * Inscribir y desinscribir un Jugador de un Equipo.
     * 
     * @param fecha
     * @param id_equipolocal
     * @param id_equipovisitante
     */
    private static void insertarPartido(Date fecha, int id_equipolocal, int id_equipovisitante) {
        String query = "INSERT INTO objetos.Partidos (fecha,equipo_local_id,equipo_visitante_id) VALUES(?,?,?)";
        try {
            pStatement = connection.prepareStatement(query);
            pStatement.setDate(1, fecha);
            pStatement.setInt(2, id_equipolocal);
            pStatement.setInt(3, id_equipovisitante);
            int filasAfectadas = pStatement.executeUpdate();
            System.out.println("filas afectadas  = " + filasAfectadas);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertarEquipo(String nombre, String ciudad, String nombre_entrenador, int edad) {

        String query = "INSERT INTO objetos.Equipos(equipo_info) VALUES(Row(?, ?, ROW(?,?)))";
        try {
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, nombre);
            pStatement.setString(2, ciudad);
            pStatement.setString(3, nombre_entrenador);
            pStatement.setInt(4, edad);

            int filasAfectadas = pStatement.executeUpdate();
            System.out.println("filas afectadas  = " + filasAfectadas);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertarJugador(String nombre, int edad, int dorsal, String posicion, float altura,
            int equipo_id) {

        String query = "INSERT INTO objetos.Jugadores (datos_personales,jugador_info,equipo_id) VALUES(" +
                " ROW(?,?),ROW(?, ?, ?),?)";
        try {
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, nombre);
            pStatement.setInt(2, edad);
            pStatement.setInt(3, dorsal);
            pStatement.setString(4, posicion);
            pStatement.setFloat(5, altura);
            pStatement.setInt(6, equipo_id);

            int filasAfectadas = pStatement.executeUpdate();
            System.out.println("filas afectadas  = " + filasAfectadas);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void modificarEquipo(int idEquipo, String nombre, String ciudad, String nombreEntrenador,
            int edadEntrenador) {
        try {
            String query = "UPDATE objetos.Equipos SET equipo_info = Row(?, ?, ROW(?, ?)) WHERE equipo_id = ?";
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, nombre);
            pStatement.setString(2, ciudad);
            pStatement.setString(3, nombreEntrenador);
            pStatement.setInt(4, edadEntrenador);
            pStatement.setInt(5, idEquipo);
            int filasAfectadas = pStatement.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificarJugador(int idJugador, String nombre, int edad, int dorsal, String posicion,
            float altura, int equipoId) {
        try {
            String query = "UPDATE objetos.Jugadores SET datos_personales = ROW(?, ?), jugador_info = ROW(?, ?, ?), equipo_id = ? WHERE jugador_id = ?";
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, nombre);
            pStatement.setInt(2, edad);
            pStatement.setInt(3, dorsal);
            pStatement.setString(4, posicion);
            pStatement.setFloat(5, altura);
            pStatement.setInt(6, equipoId);
            pStatement.setInt(7, idJugador);
            int filasAfectadas = pStatement.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificarPartido(int idPartido, Date fecha, int idEquipoLocal, int idEquipoVisitante) {
        try {
            String query = "UPDATE objetos.Partidos SET fecha = ?, equipo_local_id = ?, equipo_visitante_id = ? WHERE partido_id = ?";
            pStatement = connection.prepareStatement(query);
            pStatement.setDate(1, fecha);
            pStatement.setInt(2, idEquipoLocal);
            pStatement.setInt(3, idEquipoVisitante);
            pStatement.setInt(4, idPartido);
            int filasAfectadas = pStatement.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void eliminarPartido(int idPartido) {
        try {
            String query = "DELETE FROM objetos.Partidos WHERE partido_id = ?";
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, idPartido);
            int filasAfectadas = pStatement.executeUpdate();
            System.out.println("filas afectadas  = " + filasAfectadas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void eliminarJugador(int idJugador) {
        try {
            String query = "DELETE FROM objetos.Jugadores WHERE jugador_id = ?";
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, idJugador);
            int filasAfectadas = pStatement.executeUpdate();
            System.out.println("filas afectadas  = " + filasAfectadas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void eliminarEquipo(int id_equipo) {
        try {
            String query = "DELETE FROM objetos.Equipos where equipo_id=?";
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id_equipo);
            int filasAfectadas = pStatement.executeUpdate();
            System.out.println("filas afectadas  = " + filasAfectadas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // METODOS INTRODUCCION DE DATOS
    private static Date pedirFecha(String mensaje) {
        Date date = null;
        while (true) {
            try {
                LocalDate fecha = LocalDate.of(pedirInt("Introduce el año"),
                        pedirInt("Introduce numero de mes"), pedirInt("Introduce dia del mes"));
                date = Date.valueOf(fecha);
                return date;
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }

    private static Float pedirFloat(String mensaje) {
        float entrada = 0f;
        while (true) {
            System.out.println(mensaje);
            try {
                entrada = sc.nextFloat();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Debes introducir un float");
            }
            sc.nextLine();
            return entrada;
        }

    }

    public static String pedirString(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String entrada = "";
            try {
                entrada = sc.next();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Debes introducir un string");

            }
            sc.nextLine();

            return entrada;
        }

    }

    public static int pedirInt(String mensaje) {
        System.out.println(mensaje);
        int entrada = -1;
        while (true) {

            try {
                entrada = sc.nextInt();
            } catch (InputMismatchException e) {
                // e.printStackTrace();
                System.out.println("Debes introducir un integer");
                sc.nextLine();
            }

            return entrada;
        }
    }

    /**
     * * Crear métodos para realizar las siguientes consultas:
     *
     * Listar toda la información de un Equipo buscándolo por id.
     * Listar toda la información de todos los Equipos.
     * Listar la información de un Jugador buscándolo por id.
     * Listar la información de un Jugador buscándolo por nombre.
     * Buscar partidos en los que un determinado equipo jugara como local.
     * Buscar partidos en los que un determinado equipo jugara como visitante.
     * Obtener toda la información de los jugadores que jueguen en una determinada
     * posición.
     * Obtener toda la información de los jugadores según su dorsal.
     * Obtener todos los partidos según la fecha.
     * 
     */

    public static void listarEquipoPorId(int idEquipo) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT equipo_info.nombre, equipo_info.ciudad, equipo_info.entrenador_persona.nombre, equipo_info.entrenador_persona.edad FROM Equipos WHERE equipo_id = ?");
            statement.setInt(1, idEquipo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString(1);
                String ciudad = resultSet.getString(2);
                String entrenadorNombre = resultSet.getString(3);
                int entrenadorEdad = resultSet.getInt(4);

                System.out.println("Nombre: " + nombre + ", Ciudad: " + ciudad + ", Entrenador: " + entrenadorNombre + ", Edad: " + entrenadorEdad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarTodosEquipos() {
        try {
            String query = "SELECT equipo_id,(equipo_info).nombre AS nombre,(equipo_info).ciudad as ciudad,((equipo_info).entrenador_persona).nombre AS nombre_entrenador,((equipo_info).entrenador_persona).edad as edad FROM objetos.Equipos";
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int numCols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                System.out.println("ID Equipo: " + rs.getInt("equipo_id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Ciudad: " + rs.getString("ciudad"));
                System.out.println("Entrenador: " +
                        rs.getString("nombre_entrenador"));
                System.out.println("Edad del entrenador: " +
                        rs.getInt("edad"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarJugadorPorId(int idJugador) {
        try {
            pStatement = connection.prepareStatement("SELECT datos_personales.nombre, datos_personales.edad, jugador_info.dorsal, jugador_info.posicion, jugador_info.altura FROM Jugadores WHERE jugador_id = ?");
           
            pStatement.setInt(1, idJugador);
            ResultSet rs = pStatement.executeQuery();

            leerResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarJugadorPorNombre(String nombreJugador) {
        try {
            pStatement = connection.prepareStatement("SELECT datos_personales.nombre, datos_personales.edad, jugador_info.dorsal, jugador_info.posicion, jugador_info.altura FROM Jugadores WHERE datos_personales.nombre = ?");
            pStatement.setString(1, nombreJugador);
            ResultSet rs = pStatement.executeQuery();
            leerResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void buscarPartidosLocal(int idEquipo) {
        try {
            String query = "SELECT * FROM objetos.Partidos WHERE equipo_local_id = ?";
            
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, idEquipo);
            ResultSet rs = pStatement.executeQuery();
            leerResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void buscarPartidosVisitante(int idEquipo) {
        try {
            String query = "SELECT * FROM objetos.Partidos WHERE equipo_visitante_id = ?";
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, idEquipo);
            ResultSet rs = pStatement.executeQuery();
            leerResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void obtenerJugadoresPorPosicion(String posicion) {
        try {
            String query = "SELECT datos_personales.nombre, datos_personales.edad, jugador_info.dorsal, jugador_info.posicion, jugador_info.altura FROM Jugadores WHERE jugador_info.posicion = ?";
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, posicion);
            ResultSet rs = pStatement.executeQuery();
            leerResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void obtenerJugadoresPorDorsal(int dorsal) {
        try {
            String query = "SELECT datos_personales.nombre, datos_personales.edad, jugador_info.dorsal, jugador_info.posicion, jugador_info.altura FROM Jugadores WHERE jugador_info.dorsal = ?";
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, dorsal);
            ResultSet rs = pStatement.executeQuery();
            leerResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void obtenerPartidosPorFecha(Date fecha) {
        try {
            String query = "SELECT * FROM objetos.Partidos WHERE fecha = ?";
            pStatement = connection.prepareStatement(query);
            pStatement.setDate(1, fecha);
            ResultSet rs = pStatement.executeQuery();
            leerResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void leerResultSet(ResultSet rs) {

        int numCols;
        try {
            numCols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= numCols; i++) {
                    System.out.print(rs.getMetaData().getColumnName(i) + ": " + rs.getString(i) + "\n");

                }
                System.out.println("=======================================");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
