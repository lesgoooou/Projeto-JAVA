package Controller;

import Model.SessaoUsuario;
import Model.Musica;
import Model.Usuario;
import View.Menu;
import View.Playlists;
import Model.PlayList;
import DAO.PlaylistDAO;
import DAO.Conexao;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import DAO.MusicaDAO;
import java.security.Timestamp;
import javax.swing.JTextField;

public class PlaylistController {
    private Playlists tela_playlist;
    JTextField nomePlaylist = new JTextField();
    
    public PlaylistController(Playlists tela_playlist) {
        this.tela_playlist = tela_playlist;
    }
    public void buscarPesquisa(){
        Conexao conexao = new Conexao();
        
        String caixa_pesq = tela_playlist.getTxt_pesquisa().getText();
        String filtro = tela_playlist.getFiltro().getSelectedItem().toString();
        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            ResultSet res = dao.buscaPesquisa(caixa_pesq,filtro);
            
            JList<String> lista_playlist = tela_playlist.getLista();
            DefaultListModel<String> modelo_playlist = new DefaultListModel<>();
            
            while(res.next()){
                int id = res.getInt(1);
                String genero = res.getString(2);
                String artista = res.getString(3);
                String nome = res.getString(4);
                Boolean curtida = res.getBoolean(5);
             
                Musica musica = new Musica(nome, genero,artista, id, curtida);
                modelo_playlist.addElement(musica.toString()); 
            }
            lista_playlist.setModel(modelo_playlist);
            res.close();
            conn.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(tela_playlist,
                                              "Pesquisa não realizada!",
                                              "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
        }
    }
    public void buscarTodos(){
        Conexao conexao = new Conexao();

        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            ResultSet res = dao.buscaTodos();
            
            JList<String> lista_playlist = tela_playlist.getLista();
            DefaultListModel<String> modelo_playlist = new DefaultListModel<>();
            
            while(res.next()){
                int id = res.getInt(1);
                String genero = res.getString(2);
                String artista = res.getString(3);
                String nome = res.getString(4);
                Boolean curtida = res.getBoolean(5);
             
                Musica musica = new Musica(nome, genero,artista, id, curtida);
                modelo_playlist.addElement(musica.toString()); 
            }
            lista_playlist.setModel(modelo_playlist);
            res.close();
            conn.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(tela_playlist,
                                              "Pesquisa não realizada!",
                                              "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
        }
    }
    Object[] mensagem = {
    "Nome da Playlist:", nomePlaylist
    };
    
    public void criarPlaylist(){
        Conexao conexao = new Conexao();

        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            
            Usuario usuarioLogado = SessaoUsuario.getUsuario();
            String userId = null;
            if (usuarioLogado != null){
                userId = usuarioLogado.getUsuario();
            } 
            
            int opcao = JOptionPane.showConfirmDialog(
            null, 
            mensagem, 
            "Criar Nova Playlist", 
            JOptionPane.OK_CANCEL_OPTION
            );

            if (opcao == JOptionPane.OK_OPTION) {
                String nome = nomePlaylist.getText().trim();
            if (!nome.isEmpty()) {
                ResultSet res = dao.criarPlaylist(userId,nome);
            } else {
                JOptionPane.showMessageDialog(null, "O nome da playlist não pode estar vazio!");
            }
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(tela_playlist,
                                              "Criação de Playlist falhou!",
                                              "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void buscarPlaylists(){
        Conexao conexao = new Conexao();

        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            
            JList<String> lista_playlist = tela_playlist.getLista();
            DefaultListModel<String> modelo_playlist = new DefaultListModel<>();
            
            Usuario usuarioLogado = SessaoUsuario.getUsuario();
            String userId = null;
            if (usuarioLogado != null){
                userId = usuarioLogado.getUsuario();
            } 
            ResultSet res = dao.buscaPlaylists(userId);
            while(res.next()){
                int id = res.getInt(1);
                String nome = res.getString(3);
                java.sql.Timestamp dataCriacao = res.getTimestamp(4); 
                
                PlayList play = new PlayList(nome,userId,id,dataCriacao);
                modelo_playlist.addElement(play.toString()); 
            }
            lista_playlist.setModel(modelo_playlist);
            res.close();
            conn.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(tela_playlist,
                                              "Playlists não encontradas!",
                                              "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
        }
    }

    public void VoltarMenu(){
        tela_playlist.setVisible(false);
        new Menu().setVisible(true);
    }
}
