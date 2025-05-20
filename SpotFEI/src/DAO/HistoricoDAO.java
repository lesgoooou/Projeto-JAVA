package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class HistoricoDAO {
    private Connection conn;

    public HistoricoDAO(Connection conn) {
        this.conn = conn;
    }
    public void salvarHistorico(String userId, int idMusica) throws SQLException {
    String sql = "INSERT INTO historico (user_id, id_musica, data_hora) VALUES (?, ?, NOW())";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, userId);
    stmt.setInt(2, idMusica);
    stmt.executeUpdate();

    removerExcedente(userId);
    }
    
    private void removerExcedente(String userId) throws SQLException {
    String sql = "DELETE FROM historico WHERE id_historico NOT IN ( " +
                 "SELECT id_historico FROM historico WHERE user_id = ? " +
                 "ORDER BY data_hora DESC LIMIT 10)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, userId);
    stmt.executeUpdate();
    }
    
    public ResultSet buscarHistoricoPorUsuario(String userId) throws SQLException {
    String sql = "SELECT m.nome, m.artista, m.genero, m.curtida " +
                 "FROM historico h " +
                 "JOIN musica m ON h.id_musica = m.id_musica " +
                 "WHERE h.user_id = ?";

    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, userId);
    return stmt.executeQuery();
    }
    
}
