package Controller;

import DAO.Conexao;
import DAO.MusicaDAO;
import Model.Musica;

import View.Menu;
import View.Entrada;
import View.Playlists;
import View.MusicasCurtidas;
import View.Historico;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JList;
import javax.swing.DefaultListModel;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;



import DAO.MusicasCurtidasDAO;

public class MenuController {
    private Menu tela_menu;
    private Entrada tela_inicial;
    private MusicasCurtidas tela_curtida;

    public MenuController(Menu tela_menu) {
        this.tela_menu = tela_menu;
    }
    public void Pesquisa(){
        Conexao conexao = new Conexao();
    
        String caixa_pesq = tela_menu.getTxt_pesquisa().getText();
        String filtro = tela_menu.getFiltro().getSelectedItem().toString();
        try{
            Connection conn = conexao.getConnection();
            MusicaDAO dao = new MusicaDAO(conn);
            ResultSet res = dao.pesquisa(caixa_pesq,filtro);
            
            JList<String> lista = tela_menu.getLista();
            DefaultListModel<String> modelo = new DefaultListModel<>();
            
            int count = 0;
            while (res.next()){
                int id = res.getInt(1);
                String genero = res.getString(2);
                String artista = res.getString(3);
                String nome = res.getString(4);
                Boolean curtida = res.getBoolean(5);
                
                
                Musica musica = new Musica(nome, genero, artista, id, curtida);
                modelo.addElement(musica.toString()); 
                count++;
            }
            
            lista.setModel(modelo);
            
            res.close();
            conn.close();    
            } catch(SQLException e){
            JOptionPane.showMessageDialog(tela_menu,
                                              "Pesquisa não realizada!",
                                              "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void configurarListeners() {
    JList<String> lista = tela_menu.getLista();
    
    lista.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { 
                int index = lista.locationToIndex(e.getPoint());
                if (index != -1) {
                    String item = (String) lista.getModel().getElementAt(index);
                    gerenciarCurtida(item, index);
                }
            }
        }
    });
    }
    private void gerenciarCurtida(String item, int index) {
    boolean estaCurtida = item.contains("♥");
    
    String titulo = estaCurtida ? "Gerenciar curtida" : "Curtir música";
    String mensagem = estaCurtida 
                   ? "Deseja manter \"" + item.replace(" ♥", "") + "\" na lista de curtidas?"
                   : "Deseja adicionar \"" + item + "\" à lista de curtidas?";
    
    int resposta = JOptionPane.showConfirmDialog(
        tela_menu.getFrame(),
        mensagem,
        titulo,
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );
    
    boolean curtir = estaCurtida ? (resposta == JOptionPane.YES_OPTION) : (resposta == JOptionPane.YES_OPTION);

    atualizarCurtida(index, curtir);
}

    public void atualizarCurtida(int index, boolean curtir) {
        try {
            JList<String> lista = tela_menu.getLista();
            String musicaStr = (String) lista.getModel().getElementAt(index);
            musicaStr = musicaStr.replace(" ♥", "");
        
            String nome = extrairNomeMusica(musicaStr);
            String artista = extrairArtistaMusica(musicaStr);
        
            Conexao conexao = new Conexao();
            Connection conn = conexao.getConnection();
        
            MusicaDAO dao = new MusicaDAO(conn);
            dao.atualizarCurtida(nome, artista, curtir);
        
            Pesquisa(); // Atualizo direto a lista chamando a função
        
            String mensagem = curtir ? "Música adicionada às curtidas!" : "Música removida das curtidas!";
            JOptionPane.showMessageDialog(
                tela_menu.getFrame(),
                mensagem,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );
        
            conn.close();
        }catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(
            tela_menu.getFrame(),
            "Erro ao atualizar curtida: " + e.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        }
    }
    private String extrairNomeMusica(String musicaStr) {
        return musicaStr.split(" - ")[0];
    }

    private String extrairArtistaMusica(String musicaStr) {
        return musicaStr.split(" - ")[1];
    }
    
    
    public void irParaInicio(){
        tela_menu.setVisible(false);
        new Entrada().setVisible(true);
    }
    public void irParaMscCurtida(){
        tela_menu.setVisible(false);
        new MusicasCurtidas().setVisible(true);
    }
    public void irParaHistorico(){
        tela_menu.setVisible(false);
        new Historico().setVisible(true);
    }
    public void irParaPlaylist(){
        tela_menu.setVisible(false);
        new Playlists().setVisible(true);
    }  
    
}

