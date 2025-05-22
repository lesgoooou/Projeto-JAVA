package Controller;

import Model.SessaoUsuario;
import Model.Musica;
import Model.Usuario;
import Model.PlayList;

import View.Menu;
import View.Playlists;

import DAO.PlaylistDAO;
import DAO.Conexao;

import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PlaylistController {
    private Playlists tela_playlist;
    JTextField nomePlaylist = new JTextField();
    
    public void aviso(){
        JOptionPane.showMessageDialog(null,"Nesta tela você pode alternar entre Músicas e Playlists\n"+
                "\n"+
                "Dando um clique duplo quando selecionar/pesquisar músicas, você pode adiciona-las a alguma Playlist\n"+
                "\n"+
                "Agora quando selecionar playlists, pode ver quais são as músicas em cada playlist");
    }
    
    public PlaylistController(Playlists tela_playlist) {
        this.tela_playlist = tela_playlist;
    }
    private String modoAtual = "musicas";
     
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
    
    public String boxMusica(List<String> playlistsUser){
            JComboBox<String> comboBox = new JComboBox<>();

    for (String nomePlaylist : playlistsUser) {
        comboBox.addItem(nomePlaylist);
    }

    int opcao = JOptionPane.showConfirmDialog(
        null,
        comboBox,
        "Selecione a Playlist",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE
    );

    if (opcao == JOptionPane.OK_OPTION) {
        return (String) comboBox.getSelectedItem();
    }

    return null;
    }
    
    public void configurarListeners() {
            JList<String> listaMusicas = tela_playlist.getLista();
    
            JButton btnMusica = tela_playlist.getBotao_Musica();
            JButton btnPlaylist = tela_playlist.getBotao_Playlist();
    
            btnMusica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            modoAtual = "musicas";
                buscarTodos();
            }
            });
            btnPlaylist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modoAtual = "playlists";
                buscarPlaylists(); // já está pronto
            }
            });
            listaMusicas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
            String itemSelecionado = listaMusicas.getSelectedValue();
            if (itemSelecionado == null) return;

            try {
                Usuario usuarioLogado = SessaoUsuario.getUsuario();
                String userId = usuarioLogado.getUsuario();

                Connection conn = new Conexao().getConnection();
                PlaylistDAO dao = new PlaylistDAO(conn);
                if (modoAtual.equals("musicas")){
                    List<String> playlistsDoUsuario = dao.buscarNomesPlaylists(userId); 
                    String playlistEscolhida = boxMusica(playlistsDoUsuario);                
                    if (playlistEscolhida != null) {
                        String musc = extrairNomeMusica(itemSelecionado);
                        dao.adicionarMusicaNaPlaylist(musc, playlistEscolhida, userId);
                    }
                }else if (modoAtual.equals("playlists")){
                    String nomePlaylist = extrairNomePlaylist(itemSelecionado);
                    List<String> musicas = dao.buscarMusicasDaPlaylist(nomePlaylist, userId);

                    if (musicas != null && !musicas.isEmpty()) {
                        StringBuilder mensagem = new StringBuilder();
                        for (String musica : musicas){
                            mensagem.append("- ").append(musica).append("\n");
                        }
                        Object[] opcoes = {"Excluir Playlist", "Fechar"};
                        int escolha = JOptionPane.showOptionDialog(
                                null,
                                mensagem.toString(),
                                "Musicas da Playlist: " + nomePlaylist,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                opcoes,
                                opcoes[1]
                        );
                        if (escolha == JOptionPane.YES_OPTION){
                            int confirmar = JOptionPane.showConfirmDialog(
                            null,
                            "Tem certeza que deseja excluir a playlist \"" + nomePlaylist + "\"?",
                            "Confirmar Exclusão",
                            JOptionPane.YES_NO_OPTION
                            );
                            if (confirmar == JOptionPane.YES_OPTION) {
                            try {
                                dao.deletarPlaylist(nomePlaylist, userId);
                                JOptionPane.showMessageDialog(null, "Playlist excluída com sucesso!");
                                buscarPlaylists();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, "Erro ao excluir a playlist.");
                                e.printStackTrace();
                            }
                            }
                        }
                    }
                       
                } else {
                    JOptionPane.showMessageDialog(null,
                                                "Esta playlist está vazia.",
                                                "Aviso",
                                                JOptionPane.WARNING_MESSAGE);
            }
                } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao buscar playlists.");
            }
        }
    }
    });

    }
    private String extrairNomeMusica(String musicaStr) {
        return musicaStr.split(" - ")[0];
    }
    public String extrairNomePlaylist(String texto) {
    try {
        String[] partes = texto.split("Nome: ");
        if (partes.length > 1) {
            String restante = partes[1];
            return restante.split(" -")[0].trim();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return texto;
    }
    public void VoltarMenu(){
        tela_playlist.setVisible(false);
        new Menu().setVisible(true);
    }
}
