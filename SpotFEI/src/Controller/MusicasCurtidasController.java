package Controller;

import DAO.Conexao;
import DAO.MusicaDAO;
import DAO.MusicasCurtidasDAO;
import Model.Musica;

import View.Menu;
import View.MusicasCurtidas;

import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.sql.Connection;

public class MusicasCurtidasController {
    private MusicasCurtidas tela_curtida;

    public MusicasCurtidasController(MusicasCurtidas tela_curtida) {
        this.tela_curtida = tela_curtida;
    }
    
    public void configurarListeners(){
        JList<String> lista = tela_curtida.getListaCurtidas();
        
        lista.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    int index = lista.locationToIndex(e.getPoint());
                    if (index != -1){
                        String item = (String) lista.getModel().getElementAt(index);
                        gerenciarCurtida(item, index);
                    }
                }
            }           
        });        
    }

    private void gerenciarCurtida(String item, int index) {
        boolean estaCurtida = item.contains("♥");
    
        String titulo = estaCurtida ? "Remover curtida" : "Adicionar música";
        String mensagem = estaCurtida 
                   ? "Deseja remover \"" + item.replace(" ♥", "") + "\" das curtidas?"
                   : "Deseja adicionar \"" + item + "\" à lista de curtidas?";
    
        int resposta = JOptionPane.showConfirmDialog(
            tela_curtida.getFrame(),
            mensagem,
            titulo,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
    
        if (resposta == JOptionPane.YES_OPTION){
            atualizarCurtida(index, !estaCurtida);
        }
    }
    
    public void atualizarCurtida(int index, boolean curtir) {
        try {
            JList<String> lista = tela_curtida.getListaCurtidas();
            String musicaStr = (String) lista.getModel().getElementAt(index);
            musicaStr = musicaStr.replace(" ♥", "");
        
            String nome = extrairNomeMusica(musicaStr);
            String artista = extrairArtistaMusica(musicaStr);
        
            Conexao conexao = new Conexao();
            Connection conn = conexao.getConnection();
        
            MusicaDAO dao = new MusicaDAO(conn);
            dao.atualizarCurtida(nome, artista, curtir);
        
            carregarMusicasCurtidas();
        
            String mensagem = curtir ? "Música adicionada às curtidas!" : "Música removida das curtidas!";
            JOptionPane.showMessageDialog(
                tela_curtida.getFrame(),
                mensagem,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );
            conn.close();
        }catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(
            tela_curtida.getFrame(),
            "Erro ao atualizar curtida: " + e.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        }
    }
    public void carregarMusicasCurtidas(){
        try{
            Conexao conexao = new Conexao();
            Connection conn = conexao.getConnection();       
            MusicasCurtidasDAO dao = new MusicasCurtidasDAO(conn);
            
            List<Musica> musicas = dao.listarMusicasCurtidas();
            
            DefaultListModel<String> modelo = tela_curtida.getModeloCurtidas();
            modelo.clear();

            for (Musica musica : musicas) {
                modelo.addElement(musica.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(tela_curtida,
                "Erro ao carregar músicas curtidas: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String extrairNomeMusica(String musicaStr) {
        return musicaStr.split(" - ")[0];
    }

    private String extrairArtistaMusica(String musicaStr) {
        return musicaStr.split(" - ")[1];
    }
    public void VoltarMenu(){
        tela_curtida.setVisible(false);
        new Menu().setVisible(true);
    }
}
