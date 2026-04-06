/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import Controlador.clsAsignacionAplicacionPerfil;
import Controlador.clsAplicaciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Xander Reyes
 */
public class AsignacionAplicacionPerfilDAO {
        
    // Clase DAO que se encarga de realizar las operaciones
// de base de datos para la tabla Asignacion_Aplicacion_Perfil

    // Sentencia SQL para obtener todos los registros de la tabla
    private static final String SQL_SELECT_POR_PERFIL = 
    "SELECT * FROM asignacionaplicacionperfil WHERE Percodigo = ?";

private static final String SQL_SELECT_DISPONIBLES = 
    "SELECT * FROM aplicaciones WHERE Aplcodigo NOT IN (SELECT Aplcodigo FROM asignacionaplicacionperfil WHERE Percodigo = ?)";

    // Sentencia SQL para insertar un nuevo registro en la tabla
    private static final String SQL_INSERT = 
    "INSERT INTO asignacionaplicacionperfil (Aplcodigo, Percodigo, APLPins, APLPsel, APLPupd, APLPdel, APLPrep) VALUES (?,?,?,?,?,?,?)";

private static final String SQL_DELETE =
    "DELETE FROM asignacionaplicacionperfil WHERE Aplcodigo=? AND Percodigo=?";

// Asegúrate de que el nombre de la tabla sea consistente (minúsculas/mayúsculas)
private static final String SQL_UPDATE = 
    "UPDATE asignacionaplicacionperfil SET APLPins=?, APLPsel=?, APLPupd=?, APLPdel=?, APLPrep=? " + 
    "WHERE Aplcodigo=? AND Percodigo=?";

    // Sentencia SQL para consultar un registro específico según su AplCódigo
    private static final String SQL_QUERY =
    "SELECT Aplcodigo, Percodigo, APLPins, APLPsel, APLPupd, APLPdel, APLPdel FROM asignacionaplicacionperfil WHERE Percodigo=?";

//Devido a que existen errores en el DAO de los compañeros.
public boolean verificarExistenciaPerfil(int perCodigo) {
    String sql = "SELECT COUNT(*) FROM perfiles WHERE Percodigo = ?";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, perCodigo);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt(1) > 0; // Si el conteo es > 0, el perfil existe
        }
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    }
    return false;
}
public List<clsAsignacionAplicacionPerfil> obtenerAsignadas(int Percodigo) {
    List<clsAsignacionAplicacionPerfil> lista = new ArrayList<>();
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_POR_PERFIL)) {
        
        stmt.setInt(1, Percodigo);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            clsAsignacionAplicacionPerfil asignacion = new clsAsignacionAplicacionPerfil();
            asignacion.setAplcodigo(rs.getInt("Aplcodigo"));
            // ... setea los demás campos (ins, sel, upd, etc.) igual que en tu método select
            lista.add(asignacion);
        }
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    }
    return lista;
}
public boolean existePerfil(int perCodigo) {
    String sql = "SELECT COUNT(*) FROM perfiles WHERE Percodigo = ?";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, perCodigo);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    }
    return false;
}

// Método para llenar tablaAplDis
public List<clsAplicaciones> obtenerDisponibles(int Percodigo) {
    List<clsAplicaciones> lista = new ArrayList<>();
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_DISPONIBLES)) {
        
        stmt.setInt(1, Percodigo);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            clsAplicaciones app = new clsAplicaciones();
            app.setAplcodigo(rs.getInt("Aplcodigo"));
            app.setAplnombre(rs.getString("Aplnombre"));
            app.setAplestado(rs.getString("Aplestado"));
            lista.add(app);
        }
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    }
    return lista;
}

  public clsAsignacionAplicacionPerfil obtenerRegistroEspecifico(int aplCodigo, int perCodigo) {
    clsAsignacionAplicacionPerfil asig = null;
    String sql = "SELECT * FROM asignacionaplicacionperfil WHERE Aplcodigo = ? AND Percodigo = ?";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, aplCodigo);
        stmt.setInt(2, perCodigo);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            asig = new clsAsignacionAplicacionPerfil(rs.getInt("Aplcodigo"), rs.getInt("Percodigo"));
         asig.setAPLPins(rs.getString("APLPins"));
         asig.setAPLPsel(rs.getString("APLPsel"));
         asig.setAPLPupd(rs.getString("APLPupd"));
         asig.setAPLPdel(rs.getString("APLPdel"));
         asig.setAPLPrep(rs.getString("APLPrep"));
        }
    } catch (Exception e) { e.printStackTrace(); }
    return asig;
}


    // Método para actualizar un registro existente
    public int update(clsAsignacionAplicacionPerfil asignacion) {

        Connection conn = null;
        PreparedStatement stmt = null;

        // Variable para indicar cuántos registros fueron modificados
        int rows = 0;

        try {

            // Se obtiene la conexión a la base de datos
            conn = Conexion.getConnection();

            // Se muestra la consulta que se ejecutará

            // Se prepara la consulta de actualización
            stmt = conn.prepareStatement(SQL_UPDATE);

        // 1 al 5: Los permisos (S o N)
        stmt.setString(1, String.valueOf(asignacion.getAPLPins()));
        stmt.setString(2, String.valueOf(asignacion.getAPLPsel()));
        stmt.setString(3, String.valueOf(asignacion.getAPLPupd()));
        stmt.setString(4, String.valueOf(asignacion.getAPLPdel()));
        stmt.setString(5, String.valueOf(asignacion.getAPLPrep()));

        // 6 y 7: Las llaves para el WHERE
        stmt.setInt(6, asignacion.getAplcodigo());
        stmt.setInt(7, asignacion.getPercodigo());

            // Se ejecuta la actualización
            rows = stmt.executeUpdate();

            // Se muestran los registros modificados
            System.out.println("Registros actualizados:" + rows);

        } catch (SQLException ex) {

            // Se muestra el error si ocurre alguno
            ex.printStackTrace(System.out);

        } finally {

            // Se cierran los recursos utilizados
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }
        public int insert(clsAsignacionAplicacionPerfil asig) {
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setInt(1, asig.getAplcodigo());
            stmt.setInt(2, asig.getPercodigo());
            stmt.setString(3, String.valueOf(asig.getAPLPins()));
            stmt.setString(4, String.valueOf(asig.getAPLPsel()));
            stmt.setString(5, String.valueOf(asig.getAPLPupd()));
            stmt.setString(6, String.valueOf(asig.getAPLPdel()));
            stmt.setString(7, String.valueOf(asig.getAPLPrep()));
            return stmt.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); return 0; }
    }


public int guardarOActualizar(clsAsignacionAplicacionPerfil asig) {
    // Primero intentamos buscar si ya existe
    clsAsignacionAplicacionPerfil existe = obtenerRegistroEspecifico(asig.getAplcodigo(), asig.getPercodigo());
    
    if (existe == null) {
        return insert(asig); // Si no existe, lo crea
    } else {
        return update(asig); // Si existe, lo actualiza (incluyendo los RadioButtons)
    }
}
public int borrarRegistroEspecifico(int aplCodigo, int perCodigo) {
    String sql = "DELETE FROM asignacionaplicacionperfil WHERE Aplcodigo = ? AND Percodigo = ?";
    int rows = 0;
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, aplCodigo);
        stmt.setInt(2, perCodigo);
        rows = stmt.executeUpdate();
        
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    }
    return rows;
}
public int borrarTodoDePerfil(int perCodigo) {
    String sql = "DELETE FROM asignacionaplicacionperfil WHERE Percodigo = ?";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, perCodigo);
        return stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
        return 0;
    }
}
    // Método para eliminar un registro según su AplCódigo
    public int delete(clsAsignacionAplicacionPerfil asignacion) {
 Connection conn = null;
    PreparedStatement stmt = null;
    int rows = 0;

    try {
        conn = Conexion.getConnection();
        stmt = conn.prepareStatement(SQL_DELETE);

        // Pasamos ambos parámetros: App y Perfil
        stmt.setInt(1, asignacion.getAplcodigo());
        stmt.setInt(2, asignacion.getPercodigo());

        rows = stmt.executeUpdate();
        System.out.println("Registros eliminados: " + rows);
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    } finally {
        Conexion.close(stmt);
        Conexion.close(conn);
    }
    return rows;
    }
  
}
