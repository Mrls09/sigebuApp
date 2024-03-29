package mx.edu.utez.sigebu.sigebuapp.model;

import mx.edu.utez.sigebu.sigebuapp.utils.MySQLConnection;
import mx.edu.utez.sigebu.sigebuapp.utils.SQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO{
    Connection conn;
    SQLConnection cn = new SQLConnection();
    CallableStatement cstm;

    PreparedStatement ps;
    ResultSet rs;

    public List findAll(){
        List<UsuarioBean> usuarioBeans = new ArrayList<>();
        String query = "SELECT * FROM USUARIO;";
        try{
            conn = new SQLConnection().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                UsuarioBean usuarioBean = new UsuarioBean();
                usuarioBean.setId(rs.getInt(1));
                usuarioBean.setNombre(rs.getString(2));
                usuarioBean.setApellidos(rs.getString(3));
                usuarioBean.setCurp(rs.getString(4));
                usuarioBean.setMatricula(rs.getString(5));
                usuarioBean.setEdad(rs.getInt(6));
                usuarioBean.setTipo(rs.getString(7));
                usuarioBean.setDireccion(rs.getString(8));
                usuarioBean.setPassword(rs.getString(9));
                usuarioBean.setEmail(rs.getString(10));
                usuarioBeans.add(usuarioBean);
            }

        }catch (SQLException e){
            Logger.getLogger(UsuarioDAO.class.getName())
                    .log(Level.SEVERE, "Error findAll", e);
        }finally {
            closeConnection();
        }
        return usuarioBeans;
    }
    public boolean agregar(UsuarioBean usuarioBean){
        int r=0;
        String query = "INSERT INTO USUARIO"+
        "nombre, apellidos, matricula, edad, tipo, direccion, password_user,email"+
                "VALUES (?,?,?,?,?,?,?,?)";
        try{
            conn= new MySQLConnection().getConnection();
            ps=conn.prepareStatement(query);
            ps.setString(1, usuarioBean.getNombre());
            ps.setString(2, usuarioBean.getApellidos());
            ps.setString(3, usuarioBean.getMatricula());
            ps.setInt(4, usuarioBean.getEdad());
            ps.setString(5, usuarioBean.getTipo());
            ps.setString(6, usuarioBean.getDireccion());
            ps.setString(7, usuarioBean.getPassword());
            ps.setString(8, usuarioBean.getEmail());
            return ps.executeUpdate() == 1;

        }catch (Exception e){
            Logger.getLogger(UsuarioDAO.class.getName())
                    .log(Level.SEVERE, "Error save", e);
            return false;
        }finally {
            closeConnection();
        }

    }
    public boolean save(UsuarioBean usuarioBean){
        conn= new MySQLConnection().getConnection();
        String query = "INSERT INTO USUARIO"+
                "(nombre_usuario, apellidos_usuario, matricula_usuario, edad_usuario, tipo_usuario, direccion_usuario, correo_usuario, contraseña_usuario) "+
                "VALUES (?,?,?,?,?,?,?,?)";

        try{
            ps=conn.prepareStatement(query);
            ps.setString(1, usuarioBean.getNombre());
            ps.setString(2, usuarioBean.getApellidos());
            ps.setString(3, usuarioBean.getMatricula());
            ps.setInt(4, usuarioBean.getEdad());
            ps.setString(5, usuarioBean.getTipo());
            ps.setString(6, usuarioBean.getDireccion());
            ps.setString(7, usuarioBean.getPassword());
            ps.setString(8, usuarioBean.getEmail());
            return ps.executeUpdate() == 1;
        }catch (SQLException e){
            Logger.getLogger(UsuarioDAO.class.getName())
                    .log(Level.SEVERE, "Error save", e);
            return false;
        }finally {
            closeConnection();
        }
    }
    public boolean update(UsuarioBean usuarioBean){
        try {
            conn = new SQLConnection().getConnection();
            String query ="UPDATE usuario SET nombre = ?, apellidos = ?,"+
                    "curp = ?, matricula = ?, edad = ?, tipo = ?,"+
                    "direccion = ?, password_user = ?, email = ? where id = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, usuarioBean.getNombre());
            ps.setString(2, usuarioBean.getApellidos());
            ps.setString(3, usuarioBean.getCurp());
            ps.setString(4, usuarioBean.getMatricula());
            ps.setInt(5, usuarioBean.getEdad());
            ps.setString(6, usuarioBean.getTipo());
            ps.setString(7, usuarioBean.getDireccion());
            ps.setString(8, usuarioBean.getPassword());
            ps.setString(9, usuarioBean.getEmail());
            return ps.executeUpdate() ==1;
        }catch (SQLException e){
            Logger.getLogger(UsuarioDAO.class.getName())
                    .log(Level.SEVERE, "Error save", e);
            return false;
        }finally {
            closeConnection();
        }
    }
    public UsuarioBean findOne(int id){
        try{
            conn = new SQLConnection().getConnection();
            String query= "SELECT * FROM usuario WHERE id = ?;";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()){
                UsuarioBean usuarioBean = new UsuarioBean();
                usuarioBean.setId(rs.getInt("id"));
                usuarioBean.setNombre(rs.getString("nombre"));
                usuarioBean.setApellidos(rs.getString("apellidos"));
                usuarioBean.setCurp(rs.getString("curp"));
                usuarioBean.setMatricula(rs.getString("matricula"));
                usuarioBean.setEdad(rs.getInt("edad"));
                usuarioBean.setTipo(rs.getString("tipo"));
                usuarioBean.setDireccion(rs.getString("direccion"));
                usuarioBean.setPassword(rs.getString("password_user"));
                usuarioBean.setEmail(rs.getString("email"));
                return usuarioBean;
            }
        }catch (SQLException e){
            Logger.getLogger(UsuarioDAO.class.getName())
                    .log(Level.SEVERE, "Error save", e);
        }finally {
            closeConnection();
        }
        return null;
    }

    public UsuarioBean validarAlumno(String matricula, String password_user){
        UsuarioBean usuarioBean = new UsuarioBean();
        String query= "SELECT * FROM usuario WHERE matricula = ? AND password_user = ?";
        conn = new SQLConnection().getConnection();
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1, usuarioBean.getMatricula());
            ps.setString(2, usuarioBean.getPassword());
            rs = ps.executeQuery();
            while(rs.next()){
                usuarioBean.setId(rs.getInt("id"));
                usuarioBean.setNombre(rs.getString("nombre"));
                usuarioBean.setApellidos(rs.getString("apellidos"));
                usuarioBean.setCurp(rs.getString("curp"));
                usuarioBean.setMatricula(rs.getString("matricula"));
                usuarioBean.setEdad(rs.getInt("edad"));
                usuarioBean.setTipo(rs.getString("tipo"));
                usuarioBean.setDireccion(rs.getString("direccion"));
                usuarioBean.setPassword(rs.getString("password_user"));
                usuarioBean.setEmail(rs.getString("email"));
            }
        }catch (Exception e){
            Logger.getLogger(UsuarioDAO.class.getName())
                    .log(Level.SEVERE, "Error save", e);

        }finally {
            closeConnection();
        }
        return usuarioBean;
    }
    public void closeConnection(){
        try{
            if(conn != null){
                conn.close();
            }
            if (ps != null){
                ps.close();
            }
            if (cstm != null){
                cstm.close();
            }
            if (rs != null){
                rs.close();
            }
        }catch (SQLException e){

        }
    }
}
