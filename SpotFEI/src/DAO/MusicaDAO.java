package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import Model.Musica;
import View.Menu;

public class MusicaDAO {
    private Connection conn;
    
    public MusicaDAO(Connection conn){
        this.conn = conn;
    }
    public ResultSet pesquisa(String caixa_pesq, String filtro) throws SQLException{
        String sql = "select * from musica where "+ filtro+ " ilike ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, "%"+caixa_pesq+"%");
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        return resultado;
    }
}
