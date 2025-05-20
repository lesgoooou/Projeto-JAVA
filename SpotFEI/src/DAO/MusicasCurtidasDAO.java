package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import Model.Musica;
import View.MusicasCurtidas;
import java.util.List;
import java.util.ArrayList;


public class MusicasCurtidasDAO {
    private Connection conn;

    public MusicasCurtidasDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<Musica> listarMusicasCurtidas() throws Exception {
        List<Musica> musicas = new ArrayList<>();

        String sql = "SELECT * FROM musica WHERE curtida = true";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet res = stmt.executeQuery()) {

            while (res.next()) {
                int id = res.getInt("id_musica");
                String genero = res.getString("genero");
                String artista = res.getString("artista");
                String nome = res.getString("nome");

                Musica musica = new Musica(nome, genero, artista, id, true);
                musicas.add(musica);
            }
        }

        conn.close();
        return musicas;
    }
}
