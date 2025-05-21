package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import Model.PlayList;
import View.Playlists;
import java.util.ArrayList;
import java.util.List;

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
    
    public void adicionarMusicaNaPlaylist(String nomeMusica, String nomePlaylist, String userId) throws SQLException {
        String sql = "INSERT INTO playlists_musicas (id_playlist, id_musica) " +
                 "VALUES ( " +
                 " (SELECT id_playlist FROM playlists WHERE nome_playlist = ? AND user_id = ?), " +
                 " (SELECT id_musica FROM musica WHERE nome = ?))";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nomePlaylist);
        stmt.setString(2, userId);
        stmt.setString(3, nomeMusica);

        stmt.executeUpdate();
        stmt.close();
    }
    
    public List<String> buscarMusicasDaPlaylist(String nomePlaylist, String userId) throws SQLException {
        List<String> musicas = new ArrayList<>();

        String sql = "SELECT m.nome FROM musica m " +
                 "JOIN playlists_musicas pm ON m.id_musica = pm.id_musica " +
                 "JOIN playlists p ON p.id_playlist = pm.id_playlist " +
                 "WHERE p.nome_playlist = ? AND p.user_id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nomePlaylist);
        stmt.setString(2, userId);

        ResultSet res = stmt.executeQuery();
        while (res.next()) {
            musicas.add(res.getString("nome"));
    }

    res.close();
    stmt.close();

    return musicas;
    }
    
    public List<String> buscarNomesPlaylists(String userId) throws SQLException {
        List<String> nomes = new ArrayList<>();
        String sql = "SELECT nome_playlist FROM playlists WHERE user_id = ?";
    
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, userId);
        ResultSet res = statement.executeQuery();

        while (res.next()) {
            nomes.add(res.getString("nome_playlist"));
        }
        res.close();
        statement.close();
        return nomes;
    }
    
    public void deletarPlaylist(String nomePlaylist, String userId) throws SQLException {
        String sql = "DELETE FROM playlists WHERE nome_playlist = ? AND user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nomePlaylist);
        stmt.setString(2, userId);
        stmt.executeUpdate();
        stmt.close();
    }
}

