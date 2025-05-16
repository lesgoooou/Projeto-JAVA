package Controller;

import DAO.Conexao;
import DAO.MusicaDAO;
import Model.Musica;

import View.Menu;
import View.Entrada;
import View.Playlists;
import View.Msc_Curtidas;
import View.Historico;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JList;
import javax.swing.DefaultListModel;

public class MenuController {
    private Menu tela_menu;
    private Entrada tela_inicial;
    
    // lista eba
    private DefaultListModel<Musica> modelo;

    public MenuController(Menu tela_menu) {
        this.tela_menu = tela_menu;
    }
    public void Pesquisa(){
        Conexao conexao = new Conexao();
    
        String caixa_pesq = tela_menu.getTxt_pesquisa().getText();
        String filtro = tela_menu.getFiltro().getSelectedItem().toString();
        try{
            JList<Musica> lista = tela_menu.getLista();
            
            Connection conn = conexao.getConnection();
            MusicaDAO dao = new MusicaDAO(conn);
            ResultSet res = dao.pesquisa(caixa_pesq,filtro);
            
            while (res.next()){ // A partir daqui foi pra bosta
                int id = res.getInt(1);
                String genero = res.getString(2);
                String artista = res.getString(3);
                String nome = res.getString(4);
                Boolean curtida = res.getBoolean(5);
                System.out.println(res);
                
                if (lista != null) {
                DefaultListModel<Musica> modelo = (DefaultListModel<Musica>) lista.getModel();
                Musica teste = new Musica(nome, genero, artista, id, curtida);
                    
                modelo.addElement(teste);
                }
                
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(tela_menu,
                                              "Pesquisa não realizada!",
                                              "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
        }
    }
    public void irParaInicio(){
        tela_menu.setVisible(false);
        new Entrada().setVisible(true);
    }

}

