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
     * Debe insertar datos suficientes que permita verificar el correcto funcionamiento de las siguientes consultas.
     * Crear los métodos que permitan:
     *
     * insertar, eliminar y modificar un Equipo.
     * insertar, eliminar y modificar un Jugador.
     * insertar, eliminar y modificar un Partido.
     * Inscribir y desinscribir un Jugador de un Equipo.
     * Crear métodos para realizar las siguientes consultas:
     *
     * Listar toda la información de un Equipo buscándolo por id.
     * Listar toda la información de todos los Equipos.
     * Listar la información de un Jugador buscándolo por id.
     * Listar la información de un Jugador buscándolo por nombre.
     * Buscar partidos en los que un determinado equipo jugara como local.
     * Buscar partidos en los que un determinado equipo jugara como visitante.
     * Obtener toda la información de los jugadores que jueguen en una determinada posición.
     * Obtener toda la información de los jugadores según su dorsal.
     * Obtener todos los partidos según la fecha.
     */
    private static Connection connection;
    static Scanner sc;
    private static PreparedStatement pStatement = null;
    private static Statement statement = null;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost/futbol";
        String user = "postgres";
        String password = "abc123";
        int option = -1;
        try {
            connection = DriverManager.getConnection(url, user, password);
            sc = new Scanner(System.in);
            do {
                System.out.println("1.- Insertar equipo\n2.-Insertar jugador\n3.- Insertar partido");
                option = sc.nextInt();
                switch (option) {
                    case 1:
                        insertarEquipo(pedirString("nombre equipo"), pedirString("ciudad equipo"),
                                pedirString("nombre entrenador"), pedirInt("edad entrenador"));
                        break;
                    case 2:
                        insertarJugador(pedirString("Nombre jugador"),pedirInt("Edad jugador"),pedirInt("Dorsal")
                                ,pedirString("Posicion"),pedirFloat("Altura"),pedirInt("Id del equipo del jugador"));
                        break;
                    case 3:
                        insertarPartido(pedirFecha("Introduce una fecha"),pedirInt("Introduce id equipo local"),pedirInt("Introduce id equipo visitante"));
                        break;
                    default:
                        break;
                }
            } while (option != 0);
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    private static void insertarPartido(Date fecha, int id_equipolocal, int id_equipovisitante) {
        String query = "INSERT INTO objetos.Partidos (fecha,equipo_local_id,equipo_visitante_id) VALUES(?,?,?)" ;
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

    private static Date pedirFecha(String mensaje) {
        LocalDate fecha = LocalDate.of(pedirInt("Introduce el año"),
                pedirInt("Introduce numero de mes"), pedirInt("Introduce dia del mes"));
        Date date = Date.valueOf(fecha);
        return  date;
    }



    private static Float pedirFloat(String mensaje) {
        float entrada=0f;
        System.out.println(mensaje);
        try {
            entrada = sc.nextFloat();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Debes introducir un float");
        }
        sc.nextLine();
        return  entrada;
    }

    public static String pedirString(String mensaje) {
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
    public static int pedirInt(String mensaje) {
        System.out.println(mensaje);
        int entrada = -1;

            try {
                entrada = sc.nextInt();
            } catch (InputMismatchException e) {
              //  e.printStackTrace();
                System.out.println("Debes introducir un integer");
                sc.nextLine();
            }


        return entrada;
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
    public static void insertarJugador(String nombre, int edad, int dorsal,String posicion,float altura,int equipo_id) {

        String query = "INSERT INTO objetos.Jugadores (datos_personales,jugador_info,equipo_id) VALUES(" +
                " ROW(?,?),ROW(?, ?, ?),?)" ;
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
}
