package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import Model.User;

public class UserDAO {
    private Connection conn;
    
    public UserDAO(Connection conn) {
        this.conn = conn;
    }
    /*
    public UserDAO(Connection conn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
    public ResultSet consultar(User usuario) throws SQLException{
        String sql = "select * from usuario where user_id = ? and senha = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, usuario.getUsuario());
        statement.setString(2, usuario.getSenha());
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        return resultado;
    }
    public void inserir(User usuario) throws SQLException{
        String sql = "insert into usuario (user_id, senha) values ('"
                      + usuario.getUsuario() + "', '"
                      + usuario.getSenha()   + "')";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }
    public void atualizar(User usuario) throws SQLException{
        String sql = "update usuario set senha = ? where user_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, usuario.getSenha());
        statement.setString(2, usuario.getUsuario());
        statement.execute();
        conn.close();
    }
    public void remover(User usuario) throws SQLException{
        String sql = "delete from usuario where user_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, usuario.getUsuario());
        statement.execute();
        conn.close();
    }
}
