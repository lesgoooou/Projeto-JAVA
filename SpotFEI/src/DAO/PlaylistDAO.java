package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import Model.PlayList;
import View.Playlists;

public class PlaylistDAO {
    private Connection conn;

    public PlaylistDAO(Connection conn) {
        this.conn = conn;
    }
    
    public ResultSet buscaTodos() throws SQLException{
            String sql = "select * from musica";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
            ResultSet resultado = statement.getResultSet();
            return resultado;
    }
    public ResultSet buscaPesquisa(String caixa_pesq, String filtro) throws SQLException{
            String sql = "select * from musica where "+ filtro+" ilike ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,"%"+caixa_pesq+"%");
            statement.execute();
            ResultSet resultado = statement.getResultSet();
            return resultado;
    }
    public ResultSet criarPlaylist(String user_Id, String nome) throws SQLException{
            String sql = "insert into playlists (user_id, nome_playlist,data_criacao) values (?,?,NOW())";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,user_Id);
            statement.setString(2,nome);
            statement.execute();
            ResultSet resultado = statement.getResultSet();
            return resultado;
    }
    public ResultSet buscaPlaylists(String user_Id) throws SQLException{
            String sql = "select * from playlists where user_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,user_Id);
            statement.execute();
            ResultSet resultado = statement.getResultSet();
            return resultado;
    }
    
}
