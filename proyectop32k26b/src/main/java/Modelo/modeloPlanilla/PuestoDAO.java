package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsPuesto;
import Controlador.clsBitacora;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Conexion;
import javax.swing.table.DefaultTableModel;

public class PuestoDAO {
    
    private static final String SQL_SELECT
            = "SELECT Puecodigo, Puenombre, Puesalario_base FROM puestos";
    
    private static final String SQL_INSERT
            = "INSERT INTO puestos (Puenombre, Puesalario_base) VALUES(?, ?)";
    
    private static final String SQL_UPDATE
            = "UPDATE puestos SET Puenombre=?, Puesalario_base=? WHERE Puecodigo=?";
    
    private static final String SQL_DELETE
            = "DELETE FROM puestos WHERE Puecodigo=?";
    
    private static final String SQL_SELECT_ID
            = "SELECT Puecodigo, Puenombre, Puesalario_base FROM puestos WHERE Puecodigo=?";
    
    private static final String SQL_BUSCAR
            = "SELECT Puecodigo, Puenombre, Puesalario_base FROM puestos WHERE Puenombre LIKE ?";
    
    private static final String SQL_TOTAL
            = "SELECT COUNT(*) AS total FROM puestos";
    
    private static final String SQL_TIENE_EMPLEADOS
            = "SELECT COUNT(*) AS total FROM empleados WHERE Puecodigo=?";
    
    public List<clsPuesto> obtenerPuestos(clsBitacora bitacora) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<clsPuesto> lista = new ArrayList<>();
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                clsPuesto p = new clsPuesto();
                p.setPuecodigo(rs.getInt("Puecodigo"));
                p.setPuenombre(rs.getString("Puenombre"));
                p.setPuesalarioBase(rs.getBigDecimal("Puesalario_base"));
                lista.add(p);
            }
            
            bitacora.setBitaccion("SELECT puestos");
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        
        return lista;
    }
    
    public int insertarPuesto(clsPuesto puesto, clsBitacora bitacora) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, puesto.getPuenombre());
            stmt.setBigDecimal(2, puesto.getPuesalarioBase());
            
            rows = stmt.executeUpdate();
            
            bitacora.setBitaccion("INSERT puesto " + puesto.getPuenombre());
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        
        return rows;
    }
    
    public int actualizarPuesto(clsPuesto puesto, clsBitacora bitacora) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, puesto.getPuenombre());
            stmt.setBigDecimal(2, puesto.getPuesalarioBase());
            stmt.setInt(3, puesto.getPuecodigo());
            
            rows = stmt.executeUpdate();
            
            bitacora.setBitaccion("UPDATE puesto " + puesto.getPuecodigo());
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        
        return rows;
    }
    
    public int eliminarPuesto(clsPuesto puesto, clsBitacora bitacora) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, puesto.getPuecodigo());
            
            rows = stmt.executeUpdate();
            
            bitacora.setBitaccion("DELETE puesto " + puesto.getPuecodigo());
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        
        return rows;
    }
    
    public clsPuesto obtenerPuestoPorId(int id, clsBitacora bitacora) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsPuesto puesto = null;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                puesto = new clsPuesto();
                puesto.setPuecodigo(rs.getInt("Puecodigo"));
                puesto.setPuenombre(rs.getString("Puenombre"));
                puesto.setPuesalarioBase(rs.getBigDecimal("Puesalario_base"));
            }
            
            bitacora.setBitaccion("SELECT puesto ID " + id);
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        
        return puesto;
    }
    
    public List<clsPuesto> buscarPuestos(String filtro, clsBitacora bitacora) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsPuesto> lista = new ArrayList<>();
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_BUSCAR);
            stmt.setString(1, "%" + filtro + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                clsPuesto p = new clsPuesto();
                p.setPuecodigo(rs.getInt("Puecodigo"));
                p.setPuenombre(rs.getString("Puenombre"));
                p.setPuesalarioBase(rs.getBigDecimal("Puesalario_base"));
                lista.add(p);
            }
            
            bitacora.setBitaccion("BUSCAR puestos: " + filtro);
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        
        return lista;
    }
    
    public int totalPuestos(clsBitacora bitacora) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int total = 0;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_TOTAL);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
            
            bitacora.setBitaccion("TOTAL puestos");
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        
        return total;
    }
    
    public boolean tieneEmpleados(int puecodigo, clsBitacora bitacora) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean tiene = false;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_TIENE_EMPLEADOS);
            stmt.setInt(1, puecodigo);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                tiene = rs.getInt("total") > 0;
            }
            
            bitacora.setBitaccion("VERIFICAR empleados en puesto " + puecodigo);
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        
        return tiene;
    }
    
    public DefaultTableModel listarPuestosEnTabla() {
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("Código");
        modelo.addColumn("Nombre del Puesto");
        modelo.addColumn("Salario Base");
        modelo.addColumn("Estado");
        
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        
        try {
            cn = Conexion.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(
                "SELECT Puecodigo, Puenombre, Puesalario_base FROM puestos"
            );
            
            while (rs.next()) {
                String codigo = rs.getString("Puecodigo");
                String nombre = rs.getString("Puenombre");
                String salario = "Q" + rs.getBigDecimal("Puesalario_base").toString();
                String estado = "Activo";
                
                modelo.addRow(new Object[]{
                    codigo,
                    nombre,
                    salario,
                    estado
                });
            }
            
        } catch (Exception e) {
            System.out.println("Error al listar puestos");
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (cn != null) cn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return modelo;
    }
}